package com.mendelu.xmoric.ukol2.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.mendelu.xmoric.burgermaster.R
import com.mendelu.xmoric.burgermaster.model.Store

class CustomMapRenderer(
    val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Store>,
) : DefaultClusterRenderer<Store>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: Store, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        when(item.type){
            "food" -> markerOptions.icon(
                BitmapDescriptorFactory.fromBitmap(
                MarkerUtil.createMarkerIconFromResource(context, R.drawable.ic_food)
            ))
            "fast_food" -> markerOptions.icon(
                BitmapDescriptorFactory.fromBitmap(
                MarkerUtil.createMarkerIconFromResource(context, R.drawable.ic_food)
            ))
            "clothes" -> markerOptions.icon(
                BitmapDescriptorFactory.fromBitmap(
                MarkerUtil.createMarkerIconFromResource(context, R.drawable.ic_food)
            ))
            "kids" -> markerOptions.icon(
                BitmapDescriptorFactory.fromBitmap(
                MarkerUtil.createMarkerIconFromResource(context, R.drawable.ic_food)
            ))
            "electronics" -> markerOptions.icon(
                BitmapDescriptorFactory.fromBitmap(
                MarkerUtil.createMarkerIconFromResource(context, R.drawable.ic_food)
            ))
        }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Store>): Boolean {
        return cluster.getSize() >= 5
    }
}