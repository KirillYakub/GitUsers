package com.example.gitusers.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitusers.data.repository.Repository
import com.example.gitusers.gitdata.local.ClientData
import com.example.gitusers.gitdata.local.GitHubRepo
import com.example.gitusers.gitdata.remote.GitHubClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun getAllUsers() = repository.getAllUsers()
}