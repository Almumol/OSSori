package almumol.ossori.core.client.dto.response

data class GithubSearchContentResponse(
    val totalCount: Long,
    val items: List<GithubSearchCodeResponse>
) {
}
