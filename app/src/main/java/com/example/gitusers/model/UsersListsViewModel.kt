package com.example.gitusers.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.gitusers.common.Resource
import com.example.gitusers.data.local.LocalOwner
import com.example.gitusers.data.local.mediator.MediatorCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersListsViewModel @Inject constructor(
    pager: Pager<Int, LocalOwner>
) : ViewModel(), MediatorCallback {
    private val _state =
        MutableStateFlow<Resource<List<LocalOwner>>>(Resource.Loading())
    val state: StateFlow<Resource<List<LocalOwner>>> = _state

    override fun updateState(resource: Resource<List<LocalOwner>>) {
        _state.value = resource
    }

    val usersPageFlow = pager
        .flow
        .cachedIn(viewModelScope)
}