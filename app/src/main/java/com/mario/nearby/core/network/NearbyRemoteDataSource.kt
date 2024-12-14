package com.mario.nearby.core.network

import com.mario.nearby.core.network.KtorHttpClient.httpClient
import com.mario.nearby.data.model.Category
import com.mario.nearby.data.model.Coupon
import com.mario.nearby.data.model.Market
import com.mario.nearby.data.model.MarketDetails
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch

object NearbyRemoteDataSource {
    private const val LOCAL_HOST_EMULATOR_BASE_URL = "http://10.0.2.2:3333"
    private const val LOCAL_HOST_PHYSICAL_BASE_URL = "http://192.168.122.1:3333"
    private const val BASE_URL = LOCAL_HOST_EMULATOR_BASE_URL

    suspend fun getCategories(): Result<List<Category>> = try {
        val categories = httpClient.get("$BASE_URL/categories")
            .body<List<Category>>()
        Result.success(categories)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getMarkets(categoryId: String): Result<List<Market>> = try {
        val markets = httpClient.get("$BASE_URL/markets/category/${categoryId}")
            .body<List<Market>>()
        Result.success(markets)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getMarketDetails(marketId: String): Result<MarketDetails> = try {
        val markets = httpClient.get("$BASE_URL/markets/${marketId}")
            .body<MarketDetails>()
        Result.success(markets)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getCoupon(marketId: String): Result<Coupon> = try {
        val coupon = httpClient.patch("$BASE_URL/coupons/${marketId}")
            .body<Coupon>()
        Result.success(coupon)
    } catch (e: Exception) {
        Result.failure(e)
    }

}