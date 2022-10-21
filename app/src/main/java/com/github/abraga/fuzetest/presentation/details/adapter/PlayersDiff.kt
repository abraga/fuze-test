package com.github.abraga.fuzetest.presentation.details.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.abraga.fuzetest.data.models.PlayerResponse

object PlayersDiff : DiffUtil.ItemCallback<Pair<PlayerResponse?, PlayerResponse?>>() {

    override fun areItemsTheSame(
        oldItem: Pair<PlayerResponse?, PlayerResponse?>,
        newItem: Pair<PlayerResponse?, PlayerResponse?>
    ) = oldItem.first?.id == newItem.first?.id

    override fun areContentsTheSame(
        oldItem: Pair<PlayerResponse?, PlayerResponse?>,
        newItem: Pair<PlayerResponse?, PlayerResponse?>
    ) = oldItem == newItem
}