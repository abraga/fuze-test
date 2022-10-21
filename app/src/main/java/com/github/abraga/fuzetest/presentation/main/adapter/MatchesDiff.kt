package com.github.abraga.fuzetest.presentation.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.abraga.fuzetest.data.models.MatchResponse

object MatchesDiff : DiffUtil.ItemCallback<MatchResponse>() {

    override fun areItemsTheSame(oldItem: MatchResponse, newItem: MatchResponse) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MatchResponse, newItem: MatchResponse) = oldItem == newItem
}