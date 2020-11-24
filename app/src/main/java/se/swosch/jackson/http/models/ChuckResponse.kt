package se.swosch.jackson.http.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ChuckResponse(
    /*
    "categories":[],
    "created_at":"2020-01-05 13:42:20.841843",
    "icon_url":"https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id":"_5dhg5xUTRalS3AfBAVTZg",
    "updated_at":"2020-01-05 13:42:20.841843",
    "url":"https://api.chucknorris.io/jokes/_5dhg5xUTRalS3AfBAVTZg",
    "value":"Chuck Norris killed the electric car."
     */

    @JsonProperty("icon_url")
    val iconUrl: String,
    @JsonProperty("id")
    val id: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("value")
    val value: String,
)