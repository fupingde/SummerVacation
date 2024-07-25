package com.example.module.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val searchQuery = MutableLiveData<String>()
}
