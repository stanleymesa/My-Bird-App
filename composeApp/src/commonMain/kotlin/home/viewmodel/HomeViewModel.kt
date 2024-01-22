package home.viewmodel

import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import home.HomeEvent
import home.HomeState
import home.model.Bird
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    private suspend fun getListBird(): List<Bird> =
        httpClient.get("https://sebi.io/demo-image-api/pictures.json").body()

    var state = mutableStateOf(HomeState())
        private set

    fun onEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            HomeEvent.GetBirds -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val data = getListBird()
                    state.value = state.value.copy(birds = data)
                }
            }
        }
    }

}