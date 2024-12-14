package com.mario.nearby.ui.screen.details

import com.mario.nearby.data.model.Rule

data class MarketDetailsUIState(
    val rules: List<Rule>? = null,
    val coupon: String? = null
)
