package com.app.forhimandforher.screens

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun BarcodeScannerScreen(onBarcodeDetected: (String) -> Unit) {
    val context = LocalContext.current

    // Solicitar el permiso de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                Log.d("BarcodeScanner", "Permiso de cámara concedido")
            } else {
                Log.d("BarcodeScanner", "Permiso de cámara denegado")
            }
        }
    )
    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CameraPreview(context = context, onBarcodeDetected = onBarcodeDetected)

        // Superposición de cuadrícula de enfoque (área de escaneo)
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(2.dp, Color.White)
        )
    }
}

@Composable
fun CameraPreview(
    context: Context,
    onBarcodeDetected: (String) -> Unit
) {
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                // Configuración de la vista previa de la cámara
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                // Configuración de análisis de imágenes para ML Kit
                val imageAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, BarcodeAnalyzer(onBarcodeDetected))
                    }

                // Selecciona la cámara trasera
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                // Inicia la cámara con la vista previa y el análisis de imágenes
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as LifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e("BarcodeScanner", "Error al iniciar la cámara: ${exc.message}", exc)
                }

            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}

class BarcodeAnalyzer(private val onBarcodeDetected: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { value ->
                            Log.d("BarcodeAnalyzer", "Código de barras detectado: $value")
                            onBarcodeDetected(value) // Llama al callback con el valor detectado
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("BarcodeAnalyzer", "Error al procesar la imagen: ${e.message}", e)
                }
                .addOnCompleteListener {
                    imageProxy.close() // Asegura el cierre de la imagen
                }
        } else {
            imageProxy.close()
        }
    }
}
