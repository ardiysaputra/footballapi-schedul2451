package com.deonico.pam2451.teams.search

import com.deonico.pam2451.model.Team

interface TeamsSearchView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Team>)
}