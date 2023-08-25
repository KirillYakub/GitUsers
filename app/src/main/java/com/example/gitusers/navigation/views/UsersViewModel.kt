package com.example.gitusers.navigation.views

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.gitusers.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun getAllUsers() = repository.getAllUsers()
    fun getAllRepos() = repository.getAllUsersRepos()
}