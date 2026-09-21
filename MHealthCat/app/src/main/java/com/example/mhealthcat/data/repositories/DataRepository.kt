package com.example.mhealthcat.data.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object ServerTimeStamp
{
    fun now(): Long {
        return System.currentTimeMillis()
    }
}

abstract class DataRepository<T : Entry>(initialItems: List<T> = emptyList()) {

    private val _items = MutableStateFlow(initialItems.sortedByDescending { it.timestamp })
    val items: StateFlow<List<T>> = _items.asStateFlow()

    fun add(draft: T) {
        val stamped = stampMetadata(draft, id = UUID.randomUUID().toString(), timestamp = ServerTimeStamp.now())
        _items.value = (_items.value + stamped).sortedByDescending { it.timestamp }
    }

    fun remove(id: String) {
        _items.value = _items.value.filterNot { it.id == id }
    }

    protected abstract fun stampMetadata(draft: T, id: String, timestamp: Long): T
}


