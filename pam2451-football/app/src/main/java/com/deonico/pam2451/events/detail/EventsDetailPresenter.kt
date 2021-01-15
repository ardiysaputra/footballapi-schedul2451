package com.deonico.pam2451.events.detail

import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.EventResponse
import com.deonico.pam2451.model.TeamResponse
import com.deonico.pam2451.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventsDetailPresenter(private val view: EventsDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventDetail(eventId: String?, homeTeamId: String?, awayTeamId: String?){
        view.showLoading()

        async(context.main) {
            async(context.main) {
                val matchDetail = bg {
                    gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventDetail(eventId)), EventResponse::class.java)
                }
                val homeTeam = bg {
                    gson.fromJson(ApiRepository().doRequest(TheSportDBApi.getTeamDetail(homeTeamId)), TeamResponse::class.java)
                }
                val awayTeam = bg {
                    gson.fromJson(ApiRepository().doRequest(TheSportDBApi.getTeamDetail(awayTeamId)), TeamResponse::class.java)
                }

                view.showDetail(matchDetail.await().events, homeTeam.await().teams, awayTeam.await().teams)
                view.hideLoading()
            }
        }
    }

}