package com.mario.nearby.ui.screen.home

import com.google.android.gms.maps.model.LatLng
import com.mario.nearby.data.model.Category
import com.mario.nearby.data.model.Market

data class HomeUIState(
    val categories: List<Category>? = null,
    val markets: List<Market>? = null,
    val marketLocations: List<LatLng>? = null
)
