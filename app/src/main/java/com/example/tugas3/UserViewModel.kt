package com.example.tugas3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _averagePrice = MutableLiveData<Double>()
    val averagePrice: LiveData<Double> = _averagePrice

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProducts()
                val avg = response.products.map { it.price }.average()
                _averagePrice.value = avg
            } catch (e: Exception) {
                _error.value = "Gagal mengambil produk: ${e.message}"
            }
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val userList = RetrofitInstance.api.getUsers()
                _users.value = userList.users
            } catch (e: Exception) {
                _error.value = "Error fetching users: ${e.message}"
            }
        }
    }
}

