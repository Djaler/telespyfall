package com.github.djaler.telespyfall.utils

import com.github.djaler.telespyfall.handlers.CallbackQueryHandler

fun <T : CallbackQueryHandler> createCallbackDataForHandler(handlerClass: Class<T>, data: String = ""): String {
    return encodeHandlerClass(handlerClass) + data
}

fun <T : CallbackQueryHandler> isCallbackForHandler(callbackData: String, handlerClass: Class<T>): Boolean {
    val digest = encodeHandlerClass(handlerClass)

    return callbackData.startsWith(digest)
}

fun <T : CallbackQueryHandler> decodeCallbackData(callbackData: String, handlerClass: Class<T>): String {
    val digest = encodeHandlerClass(handlerClass)

    return callbackData.removePrefix(digest)
}

private fun <T : CallbackQueryHandler> encodeHandlerClass(handlerClass: Class<T>): String {
    return handlerClass.canonicalName.getMD5()
}
