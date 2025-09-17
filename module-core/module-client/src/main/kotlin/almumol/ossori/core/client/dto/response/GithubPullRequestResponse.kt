package almumol.ossori.core.client.dto.response

import java.time.Instant

data class GithubPullRequestResponse(
    val number: Int,
    val title: String,
    val createdAt: Instant,
    val mergedAt: Instant?
)
