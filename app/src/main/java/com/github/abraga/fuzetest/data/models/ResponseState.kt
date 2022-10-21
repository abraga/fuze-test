package com.github.abraga.fuzetest.data.models

data class ResponseState<out T>(val status: State, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?) =  ResponseState(State.SUCCESS, data, null)

        fun <T> error(msg: String) = ResponseState(State.ERROR, null, msg)

        fun <T> loading() = ResponseState(State.LOADING, null, null)
    }
}

enum class State {
    SUCCESS,
    ERROR,
    LOADING
}