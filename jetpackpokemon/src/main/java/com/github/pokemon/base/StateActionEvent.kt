package com.github.pokemon.base

//定义网络请求状态(密封类扩展性更好)
sealed class StateActionEvent

object LoadState : StateActionEvent()

object CompletionState : StateActionEvent()

data class ErrorState(val throwable: Throwable) : StateActionEvent()