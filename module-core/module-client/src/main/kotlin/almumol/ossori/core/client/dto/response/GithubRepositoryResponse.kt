package almumol.ossori.core.client.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubRepositoryResponse(
    val name: String,
    val fullName: String,
    val openIssuesCount: Long,

    @JsonProperty("stargazers_count") val countingStar: Long,
    @JsonProperty("html_url") val githubLink: String
)

