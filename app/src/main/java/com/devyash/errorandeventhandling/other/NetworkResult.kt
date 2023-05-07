package com.devyash.errorandeventhandling.other

sealed class NetworkResult<T>(
    data: T? = null,
    message: String? = null
) {

    class Success<T>(data: T):NetworkResult<T>(data=data)
    class Error<T>(data: T?=null,message: String?):NetworkResult<T>(data=data,message=message)
    class Loading<T>():NetworkResult<T>()
}