package com.github.djaler.telespyfall.model

data class GetOrCreateResult<T : Any>(val value: T, val created: Boolean)
