package com.deonico.pam2451.events

import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.EventResponse
import com.deonico.pam2451.model.LeagueResponse
import com.deonico.pam2451.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class EventsPresenter(private val view: EventsView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextEvent(leagueId: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextEvent(leagueId)),
                        EventResponse::class.java
                )
            }

            view.hideLoading()
            view.showEventList(data.await().events)
        }
    }

    fun getPreviousEvent(leagueId: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPreviousEvent(leagueId)),
                        EventResponse::class.java
                )
            }

            view.hideLoading()
            view.showEventList(data.await().events)
        }
    }

    fun getLeague(){
        view.showLoading()

        val api = TheSportDBApi.getListLeague()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.doRequest(api),
                    LeagueResponse::class.java)
            }

            view.showLeague(data.await().countrys)
            view.hideLoading()
        }
    }

}

