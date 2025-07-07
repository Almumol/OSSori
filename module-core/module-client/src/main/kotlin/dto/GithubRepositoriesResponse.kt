package dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubRepositoriesResponse(

    @JsonProperty("total_count")
    val totalCount: Long,

    val items: List<GithubRepositoryResponse>

) {
}
