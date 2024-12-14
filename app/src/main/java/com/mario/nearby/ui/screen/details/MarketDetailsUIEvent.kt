package com.mario.nearby.ui.screen.details

sealed class MarketDetailsUIEvent {
    data class OnFetchRules(val marketId: String) : MarketDetailsUIEvent()
    data class OnFetchCoupon(val qrCodeContent: String) : MarketDetailsUIEvent()
    data object OnResetCoupon : MarketDetailsUIEvent()
}