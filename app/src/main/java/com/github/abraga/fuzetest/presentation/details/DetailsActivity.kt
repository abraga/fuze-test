package com.github.abraga.fuzetest.presentation.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.abraga.fuzetest.R
import com.github.abraga.fuzetest.data.models.MatchResponse
import com.github.abraga.fuzetest.data.models.State
import com.github.abraga.fuzetest.databinding.ActivityDetailsBinding
import com.github.abraga.fuzetest.presentation.MatchesViewModel
import com.github.abraga.fuzetest.presentation.details.adapter.PlayersAdapter
import com.github.abraga.fuzetest.utils.Constants.MATCH_STATUS_RUNNING
import com.github.abraga.fuzetest.utils.Pattern
import com.github.abraga.fuzetest.utils.format
import com.github.abraga.fuzetest.utils.isThisWeek
import com.github.abraga.fuzetest.utils.isToday
import com.github.abraga.fuzetest.utils.serializable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val viewModel: MatchesViewModel by viewModels()
    private val playersAdapter = PlayersAdapter()
    private val selectedMatch by lazy {
        intent.serializable(SELECTED_MATCH_KEY) as? MatchResponse
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buildToolbar()
        buildTime()
        buildMatchVersus()
        buildRecyclerView()
        observePlayers()
        getPlayersList()
        handleWarning()
    }

    private fun buildRecyclerView() {
        binding.rvPlayersList.adapter = playersAdapter
    }

    private fun getPlayersList() {
        selectedMatch?.let { viewModel.getPlayers(it) } ?: run {
            showError()
        }
    }

    private fun observePlayers() = with(viewModel) {
        lifecycleScope.launchWhenCreated {
            playersState.collectLatest {
                when (it.status) {
                    State.SUCCESS -> {
                        showContent()
                        playersAdapter.submitList(it.data)
                    }
                    State.ERROR -> showError()
                    State.LOADING -> showLoading()
                }
            }
        }
    }

    private fun buildToolbar() = with(binding.toolbar) {
        selectedMatch?.let {
            title = getString(
                R.string.match_league,
                it.league?.name,
                it.serie?.name
            )
        }
        setNavigationOnClickListener { finish() }
    }

    private fun buildTime() = with(binding) {
        selectedMatch?.time?.let {
            if (selectedMatch?.status == MATCH_STATUS_RUNNING) {
                tvMatchTime.text = getString(R.string.match_live)
            } else {
                tvMatchTime.text = getMatchTime(it)
            }
        }
    }

    private fun getMatchTime(time: String): String {
        return when {
            time.isToday() -> getString(R.string.match_today, time.format(Pattern.PATTERN_TIME))
            time.isThisWeek() -> time.format(Pattern.PATTERN_WEEK)
            else -> time.format(Pattern.PATTERN_DATE)
        }
    }

    private fun buildMatchVersus() = with(binding.mvMatchVersus) {
        selectedMatch?.teams?.let {
            val homeOpponent = it.getOrNull(0)?.opponent
            val awayOpponent = it.getOrNull(1)?.opponent

            setHomeTeam(homeOpponent?.name, homeOpponent?.image)
            setAwayTeam(awayOpponent?.name, awayOpponent?.image)
        }
    }

    private fun handleWarning() = with(binding.warning) {
        btWarningTryAgain.setOnClickListener {
            getPlayersList()
        }
    }

    private fun showContent() {
        binding.vfFlipper.displayedChild = 0
    }

    private fun showLoading() {
        binding.vfFlipper.displayedChild = 1
    }

    private fun showError() {
        binding.vfFlipper.displayedChild = 2
    }

    companion object {
        const val SELECTED_MATCH_KEY = "SELECTED_MATCH"
    }
}