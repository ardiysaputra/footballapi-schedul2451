package com.deonico.pam2451.events

import com.deonico.pam2451.TestContextProvider
import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.Event
import com.deonico.pam2451.model.EventResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EventsPresenterTest {

    @Mock
    private lateinit var viewTV: EventsView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenterTV: EventsPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterTV = EventsPresenter(viewTV, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextEvent() {
        
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events, events)
        val leagueId = "4331"
        //Testing response get data from server api
        `when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextEvent(leagueId)),
                EventResponse::class.java
            )
        ).thenReturn(response)

        //Testing get data
        presenterTV.getNextEvent(leagueId)

        Mockito.verify(viewTV).showLoading()
        Mockito.verify(viewTV).showEventList(events)
        Mockito.verify(viewTV).hideLoading()

    }
}