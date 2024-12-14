package com.mario.nearby.ui.components.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mario.nearby.R
import com.mario.nearby.data.model.mock.mockUserLocation
import com.mario.nearby.ui.screen.home.HomeUIState
import com.mario.nearby.ui.util.findNortheastPoint
import com.mario.nearby.ui.util.findSouthwestPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun NearbyGoogleMap(
    uiState: HomeUIState
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            mockUserLocation, 13f
        )
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true
            )
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings
    ) {
        context.getDrawable(R.drawable.ic_user_location)?.let { image ->
            Marker(
                state = MarkerState(position = mockUserLocation),
                icon = BitmapDescriptorFactory.fromBitmap(
                    image.toBitmap(
                        width = density.run { 72.dp.toPx() }.roundToInt(),
                        height = density.run { 72.dp.toPx() }.roundToInt()
                    )

                )
            )
        }

        if (!uiState.markets.isNullOrEmpty()) {
            context.getDrawable(R.drawable.img_pin)?.let { image ->
                uiState.marketLocations?.forEachIndexed { index, location ->
                    Marker(
                        state = MarkerState(position = location),
                        icon = BitmapDescriptorFactory.fromBitmap(
                            image.toBitmap(
                                width = density.run { 36.dp.toPx() }.roundToInt(),
                                height = density.run { 36.dp.toPx() }.roundToInt()
                            )
                        ),
                        title = uiState.markets[index].name
                    )
                }.also {
                    coroutineScope.launch {
                        val allMarks = uiState.marketLocations?.plus(
                            mockUserLocation
                        )

                        val southwestPoint = findSouthwestPoint(allMarks.orEmpty())
                        val northwestPoint = findNortheastPoint(allMarks.orEmpty())

                        val centerPointLatitude =
                            (southwestPoint.latitude + northwestPoint.latitude) / 2
                        val centerPointLongitude =
                            (southwestPoint.longitude + northwestPoint.longitude) / 2
                        val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(
                                LatLng(
                                    centerPointLatitude,
                                    centerPointLongitude,
                                ),
                                13f,
                                0f,
                                0f
                            )
                        )

                        delay(200)
                        cameraPositionState.animate(cameraUpdate, durationMs = 500)
                    }
                }
            }
        }
    }
}