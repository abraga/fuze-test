package com.github.abraga.fuzetest.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.abraga.fuzetest.R
import com.github.abraga.fuzetest.data.models.MatchResponse
import com.github.abraga.fuzetest.data.models.Opponents
import com.github.abraga.fuzetest.databinding.ItemMainMatchBinding
import com.github.abraga.fuzetest.utils.Constants.MATCH_STATUS_FINISHED
import com.github.abraga.fuzetest.utils.Constants.MATCH_STATUS_RUNNING
import com.github.abraga.fuzetest.utils.Pattern
import com.github.abraga.fuzetest.utils.format
import com.github.abraga.fuzetest.utils.isThisWeek
import com.github.abraga.fuzetest.utils.isToday
import com.github.abraga.fuzetest.utils.loadUrl
import java.util.*

class MatchesViewHolder private constructor(
    private val binding: ItemMainMatchBinding
) : ViewHolder(binding.root) {

    fun onBind(match: MatchResponse, onItemClicked: (MatchResponse) -> Unit) = with(binding) {
        root.setOnClickListener { onItemClicked(match) }

        buildStatus(match.status, match.time)

        buildMatchVersus(match.teams)

        buildLeague(match.league?.image, match.league?.name, match.serie?.name)
    }

    private fun buildStatus(status: String?, time: String?) = with(binding) {
        val context = binding.root.context
        when (status) {
            MATCH_STATUS_RUNNING -> {
                root.alpha = 1f
                clMatchStatus.background = getDrawable(R.drawable.drawable_status_live)
                tvMatchStatusTime.text = context.getString(R.string.match_live)
                clMatchStatus.isVisible = true
            }
            MATCH_STATUS_FINISHED -> {
                root.alpha = 0.3f
                clMatchStatus.background = getDrawable(R.drawable.drawable_status_not_started)
                tvMatchStatusTime.text = context.getString(R.string.match_ended)
                clMatchStatus.isVisible = true
            }
            else -> {
                root.alpha = 1f
                clMatchStatus.background = getDrawable(R.drawable.drawable_status_not_started)

                if (time?.isNotEmpty() == true) {
                    clMatchStatus.isVisible = true
                    tvMatchStatusTime.text = getMatchTime(time).run {
                        replaceFirstChar {
                            if (it.isLowerCase()) {
                                it.titlecase(Locale.getDefault())
                            } else {
                                it.toString()
                            }
                        }
                    }
                } else {
                    clMatchStatus.isVisible = false
                }
            }
        }
    }

    private fun buildMatchVersus(opponents: List<Opponents>?) = with(binding.mvMatchVersus) {
        opponents?.let {
            val homeOpponent = it.getOrNull(0)?.opponent
            val awayOpponent = it.getOrNull(1)?.opponent

            setHomeTeam(homeOpponent?.name, homeOpponent?.image)
            setAwayTeam(awayOpponent?.name, awayOpponent?.image)
        }
    }

    private fun buildLeague(
        imageUrl: String?,
        leagueName: String?,
        serieName: String?
    ) = with(binding) {
        val context = binding.root.context
        ivMatchLeagueLogo.loadUrl(imageUrl)
        tvMatchLeagueName.text = context.getString(R.string.match_league, leagueName, serieName)
    }

    private fun getDrawable(drawableRes: Int) = ContextCompat.getDrawable(
        binding.root.context,
        drawableRes
    )

    private fun getMatchTime(time: String): String {
        val context = binding.root.context
        return when {
            time.isToday() -> context.getString(R.string.match_today, time.format(Pattern.PATTERN_TIME))
            time.isThisWeek() -> time.format(Pattern.PATTERN_WEEK)
            else -> time.format(Pattern.PATTERN_DATE)
        }
    }

    companion object {

        fun from(parent: ViewGroup): MatchesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMainMatchBinding.inflate(layoutInflater, parent, false)
            return MatchesViewHolder(binding)
        }
    }
}