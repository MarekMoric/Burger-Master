package com.mendelu.xmoric.burgermaster.communication

import com.mendelu.xmoric.burgermaster.model.Coordinates
import com.mendelu.xmoric.burgermaster.model.Store
import com.mendelu.xmoric.ukol2.architecture.CommunicationResult
import com.mendelu.xmoric.ukol2.architecture.IBaseRemoteRepository


interface IMapRemoteRepositary: IBaseRemoteRepository {

    suspend fun getStores(): CommunicationResult<List<Store>>

    suspend fun getBrnoLimits(): CommunicationResult<List<Coordinates>>

}