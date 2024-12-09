package com.example.trexense.data.utils

open class Alert<out T>(private val content: T) {
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // allow external read but not write

    /**
     * Returns the content and prevents its use again
     */

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        }else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Return the content, even if it`s already been handled
     */

    fun peekContent(): T = content
}