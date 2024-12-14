package com.mario.nearby.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mario.nearby.core.network.NearbyRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MarketDetailsUIState())
    val uiState: StateFlow<MarketDetailsUIState> = _uiState.asStateFlow()

    fun onEvent(event: MarketDetailsUIEvent) {
        when (event) {
            is MarketDetailsUIEvent.OnFetchRules -> fetchRules(
                marketId = event.marketId
            )

            is MarketDetailsUIEvent.OnFetchCoupon -> fetchCoupon(
                qrCodeContent = event.qrCodeContent
            )

            is MarketDetailsUIEvent.OnResetCoupon -> resetCoupon()
        }
    }

    private fun fetchCoupon(qrCodeContent: String) {
        viewModelScope.launch {
            NearbyRemoteDataSource.getCoupon(
                marketId = qrCodeContent
            ).onSuccess { coupon ->
                _uiState.update { currentUIState ->
                    currentUIState.copy(
                        coupon = coupon.coupon
                    )
                }
            }.onFailure {
                _uiState.update { currentUIState ->
                    currentUIState.copy(
                        coupon = ""
                    )
                }
            }
        }
    }

    private fun fetchRules(marketId: String) {
        viewModelScope.launch {
            NearbyRemoteDataSource.getMarketDetails(
                marketId = marketId
            ).onSuccess { marketDetails ->
                _uiState.update { currentUIState ->
                    currentUIState.copy(
                        rules = marketDetails.rules
                    )
                }
            }.onFailure {
                _uiState.update { currentUIState ->
                    currentUIState.copy(
                        rules = emptyList()
                    )
                }
            }
        }
    }

    private fun resetCoupon() {
        _uiState.update { currentUIState ->
            currentUIState.copy(
                coupon = null
            )
        }
    }
}