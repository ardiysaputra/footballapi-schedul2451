package com.deonico.pam2451.teams

import com.deonico.pam2451.model.League
import com.deonico.pam2451.model.Team

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun showLeague(data: List<League>)
    fun showTeamList(data: List<Team>)

}