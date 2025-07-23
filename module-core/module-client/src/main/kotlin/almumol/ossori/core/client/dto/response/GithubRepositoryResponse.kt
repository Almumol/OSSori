package almumol.ossori.core.client.dto.response

data class GithubRepositoryResponse(
    val name: String,
    val fullName: String,
    val openIssuesCount: Long,
    val htmlUrl: String,
    val stargazersCount: Long,
)

