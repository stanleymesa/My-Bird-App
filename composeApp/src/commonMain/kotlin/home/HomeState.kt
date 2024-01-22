package home

import home.model.Bird

data class HomeState(
    val birds: List<Bird> = emptyList()
)