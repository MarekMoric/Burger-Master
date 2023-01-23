package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm
import com.google.maps.android.compose.*
import com.mendelu.xmoric.burgermaster.model.Brno
import com.mendelu.xmoric.burgermaster.model.ScreenState
import com.mendelu.xmoric.burgermaster.model.Store
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import com.mendelu.xmoric.burgermaster.ui.elements.ErrorScreen
import com.mendelu.xmoric.burgermaster.ui.elements.LoadingScreen
import com.mendelu.xmoric.burgermaster.ui.theme.LightBrown
import com.mendelu.xmoric.burgermaster.ui.theme.LightGreen
import com.mendelu.xmoric.ukol2.map.CustomMapRenderer
import org.koin.androidx.compose.getViewModel

@Composable
fun MapScreen(navigation: INavigationRouter,
              viewModel: MapScreenViewModel = getViewModel()
) {

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.vector_tint_theme_color))
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "Map Screen",
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp
//        )
//    }

    val screenState: MutableState<ScreenState<Brno>> = rememberSaveable{
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.mapScreenUiState.value.let {
        when(it){
            MapScreenUIState.Start -> {
                LaunchedEffect(it){
                    viewModel.loadData()
                }
            }

            is MapScreenUIState.Error -> {
                screenState.value = ScreenState.Error()
            }

            is MapScreenUIState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }

        }
    }

    MapScreenStates(
        screenState = screenState.value,
        navigation = navigation
    )
}

@Composable
fun MapScreenStates(screenState: ScreenState<Brno>, navigation: INavigationRouter) {
    screenState.let {
        when(it){
            is ScreenState.DataLoaded -> MapScreenContent(
                brno = it.data,
                navigation = navigation)
            is ScreenState.Error -> ErrorScreen(text = "Error")
            is ScreenState.Loading -> LoadingScreen()
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreenContent(brno: Brno, navigation: INavigationRouter) {

    val zoomLevel = 14f

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(49.205944, 16.629054),
            14f)
    }

    val context = LocalContext.current

    var clusterManager by remember {
        mutableStateOf<ClusterManager<Store>?>(null)
    }

    var clusterRenderer by remember {
        mutableStateOf<CustomMapRenderer?>(null)
    }

    var currentMarker by remember { mutableStateOf<Marker?>(null)}

    if (brno.stores!!.isNotEmpty()){
        clusterManager?.addItems(brno.stores)
        clusterManager?.cluster()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        GoogleMap(uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false),
            cameraPositionState = cameraPositionState
        ){

            MapEffect(brno.stores){ map ->
                map.addPolygon(
                    PolygonOptions()
                        .addAll(brno.limits.allCoordinates!!).strokeWidth(8f))

                if ( clusterManager == null){
                    clusterManager = ClusterManager<Store>(context, map)
                }

                if (clusterRenderer == null) {
                    clusterRenderer = CustomMapRenderer(context, map, clusterManager!!)

                }

                clusterManager?.setAnimation(true)

                clusterManager?.apply {
                    renderer = clusterRenderer
                    algorithm = NonHierarchicalDistanceBasedAlgorithm()

                    renderer.setOnClusterItemClickListener { item ->

                        if (currentMarker != null) {
                            currentMarker = null
                        }

                        map.animateCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(LatLng(item!!.latitude, item.longitude), 18f)
                        )

                        currentMarker = clusterRenderer?.getMarker(item)
                        true
                    }
                }

                map.setOnCameraIdleListener {
                    clusterManager?.cluster()
                }
            }
        }
        if (currentMarker != null){
            StoreDetail(
                marker = currentMarker!!,
            navigation = navigation)
        }
    }
}

@Composable
fun StoreDetail(marker: Marker, navigation: INavigationRouter) {
    ElevatedCard(modifier = Modifier
        .padding(bottom = 64.dp)
        .fillMaxWidth(0.5f)
        .height(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(),
    ) {
        Text(text = marker.title!!,
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp, start = 28.dp),
            textAlign = TextAlign.Center)
        Text(text = marker.snippet!!,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 32.dp),
            textAlign = TextAlign.Center)
        Button(
            onClick = { navigation.returnBack() },
            shape = RoundedCornerShape(100),
            modifier = Modifier.padding(end = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBrown,
                contentColor = Color.DarkGray),
            content = { Text(text = "Place Order", style = MaterialTheme.typography.bodySmall) },
        )
    }
}