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

    private var _uploadResponse by mutableStateOf<UploadFileResponse?>(null)
    val uploadResponse: UploadFileResponse? get() = _uploadResponse

    private var resourceData by mutableStateOf<ResponseBody?>(null)

    private var _errorMessage by mutableStateOf<String?>(null)
    val errorMessage: String? get() = _errorMessage

    fun upload(file: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = RetrofitClient.fileApiService.upload(file)
                withContext(Dispatchers.Main){
                    if(response.body()!!.code == "200") {
                        response.body()?.let {
                            _uploadResponse = it
                        } ?: run {
                            _errorMessage = "No se encontró un archivo"
                        }
                    } else{
                        _errorMessage = "Falla al subir el archivo: ${response.code()}"
                    }
                }
            } catch(e: Exception){
                _errorMessage = "Un error ha ocurrido: ${e.message}, Causa del Error: ${e.cause}"
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

    // Función para preparar el archivo como MultipartBody.Part y enviarlo a upload
    fun prepareFilePartAndUpload(context: Context, uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            // Convertir Uri en archivo temporal
            val file = File.createTempFile("temp_image", null, context.cacheDir)
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            // Crear MultipartBody.Part para el archivo
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

            upload(filePart)
        }
    }

}