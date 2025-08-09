package almumol.ossori.core.client.dto.response

import java.time.Instant

data class GithubCommitResponse(
    val sha: String,
    val commit: CommitDetail
)

data class CommitDetail(
    val message: String,
    val author: CommitAuthor
)

data class CommitAuthor(
    val name: String,
    val date: Instant
)
