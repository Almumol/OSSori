package almumol.ossori.core.client.dto.response

data class GithubMetricsResponse(
    val issueCreationRate: Double,
    val pullRequestCreationRate: Double,
    val contributorCount: Int,
    val averageMergeTime: Double,
    val starDifference: Long,
    val pullRequestAverageFirstResponseTime: Double
)
