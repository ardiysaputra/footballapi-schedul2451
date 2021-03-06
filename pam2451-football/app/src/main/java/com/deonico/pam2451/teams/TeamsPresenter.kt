package com.deonico.pam2451.teams

import com.deonico.pam2451.api.ApiRepository
import com.deonico.pam2451.api.TheSportDBApi
import com.deonico.pam2451.model.LeagueResponse
import com.deonico.pam2451.model.TeamResponse
import com.deonico.pam2451.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getLeague(){
        view.showLoading()

        val api = TheSportDBApi.getListLeague()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(api),
                    LeagueResponse::class.java)
            }

            view.showLeague(data.await().countrys)
            view.hideLoading()
        }

    }

    fun getTeamList(leagueId: String?){
        view.showLoading()

        val api = TheSportDBApi.getAllTeams(leagueId)

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(api),
                    TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    fun searchTeam(keyword: String) {

        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(keyword)),
                    TeamResponse::class.java
                )
            }

            view.hideLoading()
            view.showTeamList(data.await().teams)
        }
    }

}