package com.mario.nearby.ui.screen.home

sealed class HomeUIEvent {
    data object OnFetchCategories : HomeUIEvent()
    data class OnFetchMarketsByCategory(val categoryId: String) : HomeUIEvent()
}