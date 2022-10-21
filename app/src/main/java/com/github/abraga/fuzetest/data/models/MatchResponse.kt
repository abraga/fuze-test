package com.github.abraga.fuzetest.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MatchResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("begin_at") val time: String?,
    @SerializedName("opponents") val teams: List<Opponents>?,
    @SerializedName("league") val league: League?,
    @SerializedName("serie") val serie: Serie?
) : Serializable

data class Opponents(
    @SerializedName("opponent") val opponent: Opponent?
) : Serializable

data class Opponent(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("image_url") val image: String?
) : Serializable

data class League(
    @SerializedName("name") val name: String?,
    @SerializedName("image_url") val image: String?
) : Serializable

data class Serie(
    @SerializedName("full_name") val name: String?
) : Serializable