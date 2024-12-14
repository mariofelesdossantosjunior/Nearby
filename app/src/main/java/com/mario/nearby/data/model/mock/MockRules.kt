package com.mario.nearby.data.model.mock

import com.mario.nearby.data.model.Rule

val mockRules = listOf(
    Rule(
        id = "1",
        description = "Disponivel até 31/12/2024",
        marketId = "1234"
    ),
    Rule(
        id = "2",
        description = "Valido apenas para consumo no local",
        marketId = "56789"
    )
)