package com.mario.nearby.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mario.nearby.core.network.NearbyRemoteDataSource
import com.mario.nearby.data.model.Market
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUIEvent) {
        when (event) {
            HomeUIEvent.OnFetchCategories -> fetchCategories()
            is HomeUIEvent.OnFetchMarketsByCategory -> fetchMarketsByCategory(
                categoryId = event.categoryId
            )
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _uiState.update { currentUIState ->
                NearbyRemoteDataSource.getCategories().fold(
                    onSuccess = { categories ->
                        currentUIState.copy(
                            categories = categories
                        )
                    },
                    onFailure = { _ ->
                        currentUIState.copy(
                            categories = emptyList()
                        )
                    }
                )
            }
        }
    }

    private fun fetchMarketsByCategory(categoryId: String) {
        viewModelScope.launch {
            _uiState.update { currentUIState ->
                NearbyRemoteDataSource.getMarkets(categoryId).fold(
                    onSuccess = { markets ->
                        currentUIState.copy(
                            markets = markets,
                            marketLocations = markets.map(Market::toLocation)
                        )
                    },
                    onFailure = { _ ->
                        currentUIState.copy(
                            markets = emptyList(),
                            marketLocations = emptyList()
                        )
                    }
                )
            }
        }
    }
}