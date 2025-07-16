package almumol.ossori.core.client.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubRepositoryResponse(

    val id: Long,

    val name: String,

    val description: String?,

    @JsonProperty("html_url")
    val githubLink: String,

    @JsonProperty("stargazers_count")
    val countingStar: Long,

    @JsonProperty("open_issues_count")
    val issueCount: Long,

) {
}
