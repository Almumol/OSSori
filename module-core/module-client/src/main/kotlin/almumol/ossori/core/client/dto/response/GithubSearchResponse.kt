package almumol.ossori.core.client.dto.response

data class GithubSearchResponse(
    val totalCount: Long,
    val items: List<GithubSearchCodeResponse>
) {
}
