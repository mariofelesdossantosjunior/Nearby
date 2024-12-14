package com.mario.nearby.data.model.mock

import com.mario.nearby.data.model.Market

val mockMarkets = listOf(
    Market(
        id = "1",
        categoryId = "1",
        name = "Sabor Grill",
        description = "Churascaria com cortes nobres e buffet",
        coupons = 10,
//        rules = mockRules,
        latitude = 23.0,
        longitude = -46.0,
        address = "Av. Paulista, 1000",
        phone = "(11) 98765-4321",
        cover = "https://images.unsplash.com/photo-1517849845537-4d257902454a?w=400&h=300"
    ),
    Market(
        id = "2",
        categoryId = "2",
        name = "Café Central",
        description = "Cafeteria",
        coupons = 10,
//        rules = emptyList(),
        latitude = 23.0,
        longitude = -46.0,
        address = "Alamenda Jau, 1000",
        phone = "(11) 98765-4321",
        cover = "https://images.unsplash.com/photo-1517849845537-4d257902454a?w=400&h=300"
    )
)