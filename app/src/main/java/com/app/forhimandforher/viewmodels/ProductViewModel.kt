package com.app.forhimandforher.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.forhimandforher.models.product.CreateProductFHAH
import com.app.forhimandforher.models.product.CreateProductRH
import com.app.forhimandforher.models.product.Product
import com.app.forhimandforher.services.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel: ViewModel() {

    var _productList: ArrayList<Product> by mutableStateOf(arrayListOf())

    fun listProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.listProducts()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _productList = response.body()!!.data
                }
            }
        }
    }

    fun listProductsFHAH() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.listProductsFHAH()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _productList = response.body()!!.data
                }
            }
        }
    }

    fun listProductsRH() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.listProductsRH()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _productList = response.body()!!.data
                }
            }
        }
    }

    fun createProductFHAH(product: CreateProductFHAH) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.createProductFHAH(product)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    listProducts()
                }
            }
        }
    }

    fun createProductRH(product: CreateProductRH) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.createProductRH(product)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    listProducts()
                }
            }
        }
    }
}