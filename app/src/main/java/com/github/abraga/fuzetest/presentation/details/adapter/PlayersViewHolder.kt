package com.github.abraga.fuzetest.presentation.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.abraga.fuzetest.data.models.PlayerResponse
import com.github.abraga.fuzetest.databinding.ItemDetailsPlayersBinding
import com.github.abraga.fuzetest.utils.loadUrlRoundedCorners

class PlayersViewHolder private constructor(
    private val binding: ItemDetailsPlayersBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(players: Pair<PlayerResponse?, PlayerResponse?>) = with(binding) {
        val homePlayer = players.first
        val awayPlayer = players.second

        if (homePlayer != null) {
            clHomePlayerContainer.visibility = View.VISIBLE

            buildHomePlayer(
                homePlayer.nick,
                formatFullName(homePlayer.firstName, homePlayer.lastName),
                homePlayer.image
            )
        } else {
            clHomePlayerContainer.visibility = View.INVISIBLE
        }

        if (awayPlayer != null) {
            clAwayPlayerContainer.visibility = View.VISIBLE

            buildAwayPlayer(
                awayPlayer.nick,
                formatFullName(awayPlayer.firstName, awayPlayer.lastName),
                awayPlayer.image
            )
        } else {
            clAwayPlayerContainer.visibility = View.INVISIBLE
        }
    }

    private fun buildHomePlayer(nick: String?, name: String?, imageUrl: String?) = with(binding) {
        ivHomePlayerPhoto.loadUrlRoundedCorners(imageUrl)
        tvHomePlayerNick.text = nick
        tvHomePlayerName.text = name
    }

    private fun buildAwayPlayer(nick: String?, name: String?, imageUrl: String?) = with(binding) {
        ivAwayPlayerPhoto.loadUrlRoundedCorners(imageUrl)
        tvAwayPlayerNick.text = nick
        tvAwayPlayerName.text = name
    }

    private fun formatFullName(firstName: String?, lastName: String?) = "$firstName $lastName"

    companion object {

        fun from(parent: ViewGroup): PlayersViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemDetailsPlayersBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return PlayersViewHolder(binding)
        }
    }
}