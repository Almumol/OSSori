package almumol.ossori.core.client.dto.response

data class GithubRepositoriesResponse(
    val totalCount: Long,
    val items: List<GithubRepositoryResponse>
) {
}
