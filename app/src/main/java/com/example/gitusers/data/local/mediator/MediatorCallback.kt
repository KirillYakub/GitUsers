package com.example.gitusers.data.local.mediator

import com.example.gitusers.common.Resource
import com.example.gitusers.data.local.LocalOwner

interface MediatorCallback {
    fun updateState(resource: Resource<List<LocalOwner>>)
}