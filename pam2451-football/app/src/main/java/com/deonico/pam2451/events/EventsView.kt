package com.deonico.pam2451.events

import com.deonico.pam2451.model.Event
import com.deonico.pam2451.model.League

interface EventsView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showLeague(data: List<League>)
}