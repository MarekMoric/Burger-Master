package com.mendelu.xmoric.ukol2.architecture

import retrofit2.Response

interface IBaseRemoteRepository {
    fun <T: Any> processResponse(call: Response<T>): CommunicationResult<T> {
        try {
            if (call.isSuccessful) {
                call.body()?.let {
                    return CommunicationResult.Success(it)
                } ?: kotlin.run {
                    return CommunicationResult.Error(
                        CommunicationError(
                            call.code(),
                            call.errorBody().toString()
                        )
                    )
                }
            } else {
                return CommunicationResult.Error(
                    CommunicationError(
                        call.code(),
                        call.errorBody().toString()
                    )
                )
            }

        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }
}