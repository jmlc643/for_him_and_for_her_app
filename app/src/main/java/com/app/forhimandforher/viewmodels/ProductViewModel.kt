package com.app.forhimandforher.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.forhimandforher.models.product.DetailsProduct
import com.app.forhimandforher.models.product.create.CreateProductFHAH
import com.app.forhimandforher.models.product.create.CreateProductRH
import com.app.forhimandforher.models.product.Product
import com.app.forhimandforher.models.product.filter.FilterProductsFHAH
import com.app.forhimandforher.models.product.filter.FilterProductsRH
import com.app.forhimandforher.services.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel: ViewModel() {

    var _productList: ArrayList<Product> by mutableStateOf(arrayListOf())
    var _filteredProductList: ArrayList<DetailsProduct> by mutableStateOf(arrayListOf())

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
        viewModelScope.launch() {
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

    fun filterProductsFHAH(product: FilterProductsFHAH){
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.filterProductsFHAH(product)
            withContext(Dispatchers.Main){
                if(response.body()!!.code == "200") {
                    _filteredProductList = response.body()!!.data
                }
            }
        }
    }

    fun filterProductsRH(product: FilterProductsRH){
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.filterProductsRH(product)
            withContext(Dispatchers.Main) {
                if(response.body()!!.code == "200"){
                    _filteredProductList = response.body()!!.data
                }
            }
        }
    }

    fun searchProducts(product: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.productApiService.searchProducts(product)
            withContext(Dispatchers.Main) {
                if(response.body()!!.code == "200") {
                    _filteredProductList = response.body()!!.data
                }
            }
        }
    }
}