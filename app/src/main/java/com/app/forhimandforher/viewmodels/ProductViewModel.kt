package com.app.forhimandforher.viewmodels

import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.forhimandforher.models.Supplier
import com.app.forhimandforher.models.product.CreateProductFHAH
import com.app.forhimandforher.models.product.CreateProductRH
import com.app.forhimandforher.models.product.Product
import com.app.forhimandforher.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val _uiStateFHAH = MutableStateFlow(ProductFHAHUiState())
    val uiStateFHAH: StateFlow<ProductFHAHUiState> = _uiStateFHAH.asStateFlow()

    private val _uiStateRH = MutableStateFlow(ProductRHUiState())
    val uiStateRH: StateFlow<ProductRHUiState> = _uiStateRH.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<Message?>(null)
    val message: StateFlow<Message?> = _message.asStateFlow()

    sealed class UiEvent {
        data class UpdateFieldFHAH(val field: String, val value: String): UiEvent()
        object SubmitFHAH: UiEvent()
        data class UpdateFieldRH(val field: String, val value: String): UiEvent()
        object SubmitRH: UiEvent()
    }

    fun onEvent(event: UiEvent){
        when(event){
            is UiEvent.UpdateFieldFHAH -> updateFieldFHAH(event.field, event.value)
            is UiEvent.UpdateFieldRH -> updateFieldRH(event.field, event.value)
            is UiEvent.SubmitFHAH -> submitProductFHAH()
            is UiEvent.SubmitRH ->  submitProductRH()
        }
    }

    private fun updateFieldFHAH(field: String, value: String) {
        _uiStateFHAH.update { currentState ->
            when(field) {
                "modelo" -> currentState.copy(modelo = value)
                "codigo" -> currentState.copy(codigo = value)
                "nombre" -> currentState.copy(nombre = value)
                "proveedor" -> currentState.copy(proveedor = value)
                "talla" -> currentState.copy(talla = value)
                "color" -> currentState.copy(color = value)
                "cantidad" -> currentState.copy(cantidad = value)
                "costo" -> currentState.copy(costo = value)
                "precio" -> currentState.copy(precio = value)
                else -> currentState
            }
        }
    }


    private fun updateFieldRH(field: String, value: String){
        _uiStateRH.update { currentState ->
            when(field){
                "codigo" -> currentState.copy(codigo = value)
                "nombre" -> currentState.copy(nombre = value)
                "marca" -> currentState.copy(marca = value)
                "cantidad" -> currentState.copy(cantidad = value)
                "costo" -> currentState.copy(costo = value)
                "precio" -> currentState.copy(precio = value)
                else -> currentState
            }
        }
    }

    private fun submitProductFHAH() {
        viewModelScope.launch {
            _isLoading.value = true
            _message.value = null

            try {
                val product = _uiStateFHAH.value.toCreateProductFHAH()
                val response = RetrofitClient.productApiService.createProductFHAH(product)

                if (response.success) {
                    _message.value = Message.Success("Producto registrado exitosamente")
                    _uiStateFHAH.value = ProductFHAHUiState() // Reset form
                } else {
                    _message.value = Message.Error(response.message)
                }
            } catch (e: Exception) {
                _message.value = Message.Error("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun submitProductRH() {
        viewModelScope.launch {
            _isLoading.value = true
            _message.value = null

            try {
                val product = _uiStateRH.value.toCreateProductRH()
                val response = RetrofitClient.productApiService.createProductRH(product)

                if (response.success) {
                    _message.value = Message.Success("Producto registrado exitosamente")
                    _uiStateRH.value = ProductRHUiState() // Reset form
                } else {
                    _message.value = Message.Error(response.message)
                }
            } catch (e: Exception) {
                _message.value = Message.Error("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class ProductFHAHUiState(
    val modelo: String = "",
    val codigo: String = "",
    val nombre: String = "",
    val proveedor: String = "",
    val talla: String = "S",
    val color: String = "",
    val cantidad: String = "1",
    val costo: String = "0",
    val precio: String = "0"
) {
    fun toCreateProductFHAH() = CreateProductFHAH(
        model = modelo,
        code = codigo,
        name = nombre,
        supplierName = proveedor,
        size = talla,
        color = color,
        quantity = cantidad.toIntOrNull() ?: 1,
        purchasePrice = costo.toDoubleOrNull() ?: 0.0,
        salesPrice = precio.toDoubleOrNull() ?: 0.0
    )
}

data class ProductRHUiState(
    val marca: String = "",
    val codigo: String = "",
    val nombre: String = "",
    val cantidad: String = "1",
    val costo: String = "0",
    val precio: String = "0"
) {
    fun toCreateProductRH() = CreateProductRH(
        brandName = marca,
        code = codigo,
        name = nombre,
        quantity = cantidad.toIntOrNull() ?: 1,
        purchasePrice = costo.toDoubleOrNull() ?: 0.0,
        salesPrice = precio.toDoubleOrNull() ?: 0.0
    )
}

sealed class Message {
    data class Success(val message: String) : Message()
    data class Error(val message: String) : Message()
}