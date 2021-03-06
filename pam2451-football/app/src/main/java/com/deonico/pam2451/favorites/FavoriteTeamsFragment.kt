package com.deonico.pam2451.favorites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.deonico.pam2451.R
import com.deonico.pam2451.db.TeamDB
import com.deonico.pam2451.db.database
import com.deonico.pam2451.model.Team
import com.deonico.pam2451.teams.detail.TeamsDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamsFragment: Fragment(), AnkoComponent<Context> {
    
    private var teams: MutableList<TeamDB> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var teamList: RecyclerView
    private lateinit var adapter: FavoriteTeamsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteTeamsAdapter(teams){
            val team = Team(
                idTeam = it.idTeam,
                strTeam = it.strTeam,
                strTeamBadge = it.strTeamBadge,
                strStadium = it.strStadium,
                intFormedYear = it.intFormedYear,
                strDescriptionEN = it.strDescriptionEN
            )

            requireContext().startActivity<TeamsDetailActivity>("teamData" to team)
        }
        teamList.adapter = adapter
        
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        return createView(AnkoContext.create(requireContext()))
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.searchMenu)?.setVisible(false)
        super.onPrepareOptionsMenu(menu)
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            orientation = LinearLayout.VERTICAL

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                teamList = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    private fun showFavorite(){
        requireContext().database.use {
            swipeRefresh.isRefreshing = true
            val result = select(TeamDB.TABLE_TEAM)
            val team = result.parseList(classParser<TeamDB>())
            teams.clear()
            teams.addAll(team)
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }
}