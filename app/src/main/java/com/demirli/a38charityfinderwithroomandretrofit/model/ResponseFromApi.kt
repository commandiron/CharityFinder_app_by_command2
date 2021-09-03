package com.demirli.a38charityfinderwithroomandretrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class ResponseFromApi {
    data class SearchResponse(
        @SerializedName("search")
        @Expose
        var search: ResponseResponse
    )
    data class ResponseResponse(
        @SerializedName("response")
        @Expose
        var response: ProjectsResponse
    )
    data class ProjectsResponse(
        @SerializedName("projects")
        @Expose
        var projects: ProjectResponse
    )
    data class ProjectResponse(
        @SerializedName("project")
        @Expose
        var project: List<ProjectResults>

    )
    data class ProjectResults(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("contactAddress")
        val address: String,
        @SerializedName("projectLink")
        val projectLink: String,
        @SerializedName("imageLink")
        val imageLink: String

    )
}