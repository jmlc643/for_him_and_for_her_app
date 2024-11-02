package com.app.forhimandforher.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException

@Composable
fun BarcodeScannerScreen(onBarcodeDetected: (String) -> Unit) {
    val context = LocalContext.current

    // Solicita el permiso de cámara
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

    Box(contentAlignment = Alignment.Center) {
        BarcodeScannerView(
            context = context,
            onBarcodeDetected = onBarcodeDetected
        )
    }
}

@Composable
fun BarcodeScannerView(
    context: Context,
    onBarcodeDetected: (String) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val barcodeDetector = BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build()

            val cameraSource = CameraSource.Builder(context, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build()

            val preview = CameraSourcePreview(context)

            barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
                override fun release() {}

                override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                    val barcodes = detections.detectedItems
                    if (barcodes.size() > 0) {
                        val barcode = barcodes.valueAt(0)
                        onBarcodeDetected(barcode.displayValue) // Llama al callback con el valor detectado
                    }
                }
            })

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                try {
                    preview.start(cameraSource)
                } catch (e: IOException) {
                    Log.e("BarcodeScanner", "Error al iniciar la cámara: ${e.message}")
                } catch (e: SecurityException) {
                    Log.e("BarcodeScanner", "Permiso de cámara denegado: ${e.message}")
                }
            } else {
                Log.e("BarcodeScanner", "Permiso de cámara no concedido")
            }

            preview
        },
        update = { }
    )
}