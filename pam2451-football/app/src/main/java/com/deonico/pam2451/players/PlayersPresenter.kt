package com.deonico.pam2451.players

import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.PlayerResponse
import com.deonico.pam2451.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayersPresenter(private val view: PlayersView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson,
                       private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayers(idTeam)),
                    PlayerResponse::class.java
                )
            }
            view.hideLoading()
            view.showPlayerList(data.await().player)

        }
    }

}