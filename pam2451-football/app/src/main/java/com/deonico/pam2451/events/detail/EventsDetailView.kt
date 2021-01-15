package com.deonico.pam2451.events.detail

import com.deonico.pam2451.model.Event
import com.deonico.pam2451.model.Team

interface EventsDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(matchDetails: List<Event>,
                   homeTeams: List<Team>,
                   awayTeams: List<Team>)
}