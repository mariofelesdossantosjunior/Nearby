package com.mario.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mario.nearby.data.model.Market
import com.mario.nearby.ui.route.Home
import com.mario.nearby.ui.route.QRCodeScanner
import com.mario.nearby.ui.route.Splash
import com.mario.nearby.ui.route.Welcome
import com.mario.nearby.ui.screen.details.MarketDetailsScreen
import com.mario.nearby.ui.screen.details.MarketDetailsUIEvent
import com.mario.nearby.ui.screen.details.MarketDetailsViewModel
import com.mario.nearby.ui.screen.home.HomeScreen
import com.mario.nearby.ui.screen.home.HomeViewModel
import com.mario.nearby.ui.screen.qrcode_scanner.QRCodeScannerScreen
import com.mario.nearby.ui.screen.splash.SplashScreen
import com.mario.nearby.ui.screen.welcome.WelcomeScreen
import com.mario.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUIState by homeViewModel.uiState.collectAsStateWithLifecycle()

                val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
                val marketDetailsUIState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }
                    composable<Welcome> {
                        WelcomeScreen(
                            onNavigateToHome = {
                                navController.navigate(Home)
                            }
                        )
                    }
                    composable<Home> {
                        HomeScreen(
                            uiState = homeUIState,
                            onEvent = homeViewModel::onEvent,
                            onNavigateToMarketDetails = { selectedMarket ->
                                navController.navigate(selectedMarket)
                            }
                        )
                    }
                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()
                        MarketDetailsScreen(
                            uiState = marketDetailsUIState,
                            onEvent = marketDetailsViewModel::onEvent,
                            market = selectedMarket,
                            onNavigateToQRCodeScanner = {
                                navController.navigate(QRCodeScanner)
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable<QRCodeScanner> {
                        QRCodeScannerScreen(onCompletedScan = { qrCodeContent ->
                            if (qrCodeContent.isNotEmpty()) {
                                marketDetailsViewModel.onEvent(
                                    MarketDetailsUIEvent.OnFetchCoupon(
                                        qrCodeContent = qrCodeContent
                                    )
                                )
                                navController.popBackStack()
                            }
                        })
                    }
                }
            }
        }
    }
}