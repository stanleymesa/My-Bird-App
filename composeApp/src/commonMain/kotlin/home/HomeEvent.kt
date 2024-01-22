package home

sealed class HomeEvent {
    data object GetBirds: HomeEvent()
}