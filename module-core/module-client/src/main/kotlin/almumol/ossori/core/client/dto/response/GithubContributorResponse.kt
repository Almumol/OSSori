package almumol.ossori.core.client.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubContributorResponse(
    val contributors: List<GithubContributorDetailResponse>
)

data class GithubContributorDetailResponse(
    val login: String,
    val id: Long,
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    @JsonProperty("html_url")
    val htmlUrl: String,
    val contributions: Int
)
