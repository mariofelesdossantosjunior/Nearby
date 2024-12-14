package com.mario.nearby.ui.util

import com.google.android.gms.maps.model.LatLng

fun findSouthwestPoint(points: List<LatLng>): LatLng {
    return if (points.isEmpty()) {
        LatLng(0.0, 0.0)
    } else {
        var southwestPoint = points[0]

        for (point in points) {
            if (point.latitude < southwestPoint.latitude ||
                (point.latitude == southwestPoint.latitude && point.longitude < southwestPoint.longitude)
            ) {
                southwestPoint = point
            }
        }

        southwestPoint
    }
}

fun findNortheastPoint(points: List<LatLng>): LatLng {
    return if (points.isEmpty()) {
        LatLng(0.0, 0.0)
    } else {
        var northeastPoint = points[0]

        for (point in points) {
            if (point.latitude > northeastPoint.latitude ||
                (point.latitude == northeastPoint.latitude && point.longitude > northeastPoint.longitude)
            ) {
                northeastPoint = point
            }
        }

        northeastPoint
    }
}
