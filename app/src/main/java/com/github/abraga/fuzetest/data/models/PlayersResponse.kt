package com.github.abraga.fuzetest.data.models

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nick: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("image_url") val image: String?,
    @SerializedName("current_team") val team: PlayerTeam?
)

data class PlayerTeam(
    @SerializedName("id") val id: Int
)