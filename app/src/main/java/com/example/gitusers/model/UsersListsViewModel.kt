package com.example.gitusers.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.gitusers.data.local.LocalUser
import com.example.gitusers.data.local.toUserDisplay
import com.example.gitusers.data.remote.Users
import com.example.gitusers.data.remote.toLocalOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersListsViewModel @Inject constructor(
    pager: Pager<Int, LocalUser>
) : ViewModel() {

    val usersPageFlow = pager
        .flow
        .map {data ->
            data.map {
                it.toUserDisplay()
            }
        }
        .cachedIn(viewModelScope)
}