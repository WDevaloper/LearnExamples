package com.github.ipc.service.manager.core

import com.google.gson.annotations.SerializedName


/*
{
	"className": "UserManager",
	"methodName": "getInstance",
	"parameters": [{
		"parameterClassName": "java.lang.String",
		"parameterValue": "wangfayou"
	}],
	"type": 1
}
 */
data class RequestBean(
        @SerializedName("className")
        val className: String,
        @SerializedName("methodName")
        val methodName: String,
        @SerializedName("parameters")
        val parameters: List<Parameter>? = null,
        @SerializedName("type")
        val type: Int
)
