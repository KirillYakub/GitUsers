package com.example.gitusers.model

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.gitusers.common.Resource
import com.example.gitusers.common.connect.InternetConnection
import com.example.gitusers.data.local.LocalOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UsersListsViewModel @Inject constructor(
    pager: Pager<Int, LocalOwner>
) : ViewModel() {
    val usersPageFlow = pager
        .flow
        .cachedIn(viewModelScope)
}