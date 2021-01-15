package com.deonico.pam2451.teams

import com.deonico.pam2451.TestContextProvider
import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.Team
import com.deonico.pam2451.model.TeamResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {

    @Mock
    private lateinit var viewTV: TeamsView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenterTV: TeamsPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterTV = TeamsPresenter(viewTV, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {

        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val leagueId = "4331"
        //Testing response get data from server api
        `when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getAllTeams(leagueId)),
                TeamResponse::class.java
            )
        ).thenReturn(response)

        //Testing get data
        presenterTV.getTeamList(leagueId)

        Mockito.verify(viewTV).showLoading()
        Mockito.verify(viewTV).showTeamList(teams)
        Mockito.verify(viewTV).hideLoading()

    }

}