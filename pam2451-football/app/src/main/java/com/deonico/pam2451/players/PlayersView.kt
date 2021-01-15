package com.deonico.pam2451.players

import com.deonico.pam2451.model.Player

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}