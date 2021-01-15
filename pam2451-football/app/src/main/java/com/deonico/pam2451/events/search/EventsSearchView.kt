package com.deonico.pam2451.events.search

import com.deonico.pam2451.model.Event

interface EventsSearchView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Event>)
}