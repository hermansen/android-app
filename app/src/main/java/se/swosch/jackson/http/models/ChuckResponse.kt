package se.swosch.jackson.http.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ChuckResponse(
    @JsonProperty("icon_url")
    val iconUrl: String,
    @JsonProperty("id")
    val id: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("value")
    val value: String,
)