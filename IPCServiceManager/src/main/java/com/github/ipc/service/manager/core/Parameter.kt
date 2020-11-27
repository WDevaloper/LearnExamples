package com.github.ipc.service.manager.core

import com.google.gson.annotations.SerializedName


data class Parameter(
        @SerializedName("parameterClassName")
        val parameterClassName: String,
        @SerializedName("parameterValue")
        val parameterValue: String
)