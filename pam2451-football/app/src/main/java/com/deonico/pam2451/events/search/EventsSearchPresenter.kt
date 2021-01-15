package com.deonico.pam2451.events.search

import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.EventResponse
import com.deonico.pam2451.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventsSearchPresenter(private val view: EventsSearchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEvent(keyword: String?){
        view.showLoading()

        val api = TheSportDBApi.getEvents(keyword)

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.
                    doRequest(api),
                    EventResponse::class.java)
            }

            view.showList(data.await().event)
            view.hideLoading()
        }
    }

}