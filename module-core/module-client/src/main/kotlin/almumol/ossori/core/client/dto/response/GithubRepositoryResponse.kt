package almumol.ossori.core.client.dto.response

data class GithubRepositoryResponse(
    val name: String,
    val fullName: String,
    val openIssuesCount: Long,
    val htmlUrl: String,
    val stargazersCount: Long,
    val owner: GithubOwnerResponse,
    val description: String?,
) : SnakeCaseResponse()

data class GithubOwnerResponse(
    val login: String,
    val id: Long,
)
