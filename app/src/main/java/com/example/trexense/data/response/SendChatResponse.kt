package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class ChatResponse(

    @field:SerializedName("data")
    val data: DataChatResponse,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)

data class DataChatResponse(

    @field:SerializedName("response")
    val response: String? = null
)