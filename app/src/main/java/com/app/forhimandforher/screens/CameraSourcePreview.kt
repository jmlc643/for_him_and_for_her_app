package com.app.forhimandforher.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import java.io.IOException

class CameraSourcePreview(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private val surfaceView: SurfaceView = SurfaceView(context)
    private var cameraSource: CameraSource? = null

    init {
        addView(surfaceView)
    }

    fun start(cameraSource: CameraSource) {
        this.cameraSource = cameraSource
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // Verifica el permiso de cámara antes de intentar iniciar la cámara
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(surfaceView.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                } else {
                    // Muestra un mensaje o maneja la falta de permiso de cámara
                    println("Permiso de cámara no concedido")
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
    }

    fun stop() {
        cameraSource?.stop()
    }
}