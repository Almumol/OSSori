package almumol.ossori.core.client.dto.response

data class GithubContributorResponse(
    val contributors: List<GithubContributorDetailResponse>
)

data class GithubContributorDetailResponse(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val htmlUrl: String,
    val contributions: Int
)
