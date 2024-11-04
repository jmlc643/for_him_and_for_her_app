package com.app.forhimandforher.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.forhimandforher.models.file.UploadFileResponse
import com.app.forhimandforher.services.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

class FileViewModel: ViewModel() {

    private var resourceData by mutableStateOf<ResponseBody?>(null)

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String? get() = _errorMessage

    private var _multiPartBody: MultipartBody.Part? = null

    private var _resource: UploadFileResponse? = null
    val resource: UploadFileResponse? get() = _resource

    fun uploadFile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Verificar que _multiPartBody no sea null antes de hacer la llamada
                _multiPartBody?.let { part ->
                    // Llamada a la API usando el archivo MultipartBody.Part
                    val response = RetrofitClient.fileApiService.upload(part)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                _resource = it
                            } ?: run {
                                _errorMessage = "No se encontró un archivo"
                            }
                        } else {
                            _errorMessage = "Falla al subir el archivo: ${response.code()}"
                        }
                    }
                } ?: run {
                    _errorMessage = "Archivo no preparado para la subida"
                }
            } catch (e: Exception) {
                _errorMessage = "Un error ha ocurrido al subir el archivo: ${e.message}"
            }
        }
    }

    fun getResource(filename: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.fileApiService.getResource(filename)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            resourceData = it
                        } ?: run {
                            _errorMessage = "No se encontró un archivo"
                        }
                    } else {
                        _errorMessage = "Falla al subir el archivo: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                _errorMessage = "Un error ha ocurrido: ${e.message}"
            }
        }
    }

    // Función para preparar el archivo como MultipartBody.Part
    fun prepareFilePart(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Crear archivo temporal
                val file = File.createTempFile("temp_image", null, context.cacheDir)
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                // Crear MultipartBody.Part y almacenar en _multiPartBody
                _multiPartBody = MultipartBody.Part.createFormData(
                    "file", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                _errorMessage = "Error al preparar el archivo: ${e.message}"
            }
        }
    }



}