package com.mendelu.xmoric.ukol2.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.mendelu.xmoric.burgermaster.model.Store

class CustomMapRenderer(
    val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Store>,
) : DefaultClusterRenderer<Store>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: Store, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Store>): Boolean {
        return cluster.getSize() >= 5
    }
}