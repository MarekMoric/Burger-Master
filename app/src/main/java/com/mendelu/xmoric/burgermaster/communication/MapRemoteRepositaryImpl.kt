package com.mendelu.xmoric.burgermaster.communication

import com.mendelu.xmoric.burgermaster.model.Coordinates
import com.mendelu.xmoric.burgermaster.model.Store
import com.mendelu.xmoric.ukol2.architecture.CommunicationError
import com.mendelu.xmoric.ukol2.architecture.CommunicationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MapRemoteRepositaryImpl(private val mapAPI: MapAPI) : IMapRemoteRepositary {
    override suspend fun getStores(): CommunicationResult<List<Store>> {
        try {
            val response = withContext(Dispatchers.IO) {
                mapAPI.getStores()
            }
            if (response.isSuccessful){
                if (response.body() != null) {
                    return CommunicationResult.Success(response.body()!!)
                } else {
                    return CommunicationResult.Error(
                        CommunicationError(response.code(), response.errorBody().toString())
                    )
                }
            } else {
                return CommunicationResult.Error(
                    CommunicationError(response.code(), response.errorBody().toString())
                )
            }

        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }

    override suspend fun getBrnoLimits(): CommunicationResult<List<Coordinates>> {
//    override suspend fun getBrnoLimits(): CommunicationResult<Coordinates> {
        return try {
            processResponse(withContext(Dispatchers.IO){mapAPI.getBrnoBoundaries()})
        } catch (timeoutEx: SocketTimeoutException) {
            return CommunicationResult.Exception(timeoutEx)
        } catch (unknownHostEx: UnknownHostException) {
            return CommunicationResult.Exception(unknownHostEx)
        }
    }
}