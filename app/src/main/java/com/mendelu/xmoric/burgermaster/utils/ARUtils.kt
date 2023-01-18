package com.mendelu.xmoric.burgermaster.utils

import com.google.ar.core.Pose

object ARUtils {

    fun getDistanceToObject(object1Pose: Pose, object2Pose: Pose): Float {
        val distanceX = object1Pose.tx() - object2Pose.tx()
        val distanceY = object1Pose.ty() - object2Pose.ty()
        val distanceZ = object1Pose.tz() - object2Pose.tz()
        return Math.sqrt((distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ).toDouble())
            .toFloat()
    }
}