package com.mendelu.xmoric.burgermaster.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.maps.android.PolyUtil
import com.mendelu.xmoric.burgermaster.communication.MapRemoteRepositaryImpl
import com.mendelu.xmoric.burgermaster.model.Brno
import com.mendelu.xmoric.ukol2.architecture.BaseViewModel
import com.mendelu.xmoric.ukol2.architecture.CommunicationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapScreenViewModel(private val remoteRepository: MapRemoteRepositaryImpl): BaseViewModel() {

    val mapScreenUiState: MutableState<MapScreenUIState<Brno>> = mutableStateOf(MapScreenUIState.Start)

    fun loadData() {
        launch {
            val stores = withContext(Dispatchers.IO) {
                remoteRepository.getStores()
            }

            when (stores) {
                is CommunicationResult.Error -> {
                    mapScreenUiState.value = MapScreenUIState.Error
                }
                is CommunicationResult.Exception -> mapScreenUiState.value =
                    MapScreenUIState.Error
                is CommunicationResult.Success -> {
                    val limits = withContext(Dispatchers.IO) {
                        remoteRepository.getBrnoLimits()
                    }
                    when (limits) {
                        is CommunicationResult.Error -> mapScreenUiState.value =
                            MapScreenUIState.Error
                        is CommunicationResult.Exception -> mapScreenUiState.value =
                            MapScreenUIState.Error
                        is CommunicationResult.Success -> {
                            val filtered = stores.data.filter {
                                PolyUtil.containsLocation(
                                    it.position,
                                    limits.data[0].allCoordinates, false)
                            }

                            val brno = Brno(stores.data, limits.data[0])
                            mapScreenUiState.value = MapScreenUIState.Success(brno)
                        }
                    }
                }
            }
        }
    }


}