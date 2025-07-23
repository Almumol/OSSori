package almumol.ossori.core.client.github

import almumol.ossori.core.client.dto.response.GithubCommitResponse
import almumol.ossori.core.client.dto.response.GithubContributorResponse
import almumol.ossori.core.client.dto.response.GithubMetricsResponse
import almumol.ossori.core.client.dto.response.GithubPullRequestResponse
import almumol.ossori.core.client.dto.response.GithubRepositoryResponse
import org.springframework.stereotype.Component

@Component
abstract class GithubMetricsCalculator() {

    abstract fun calculateMetrics(
        repositorySummary: GithubRepositoryResponse,
        commits: List<GithubCommitResponse>,
        pullRequests: List<GithubPullRequestResponse>,
        contributors: List<GithubContributorResponse>
    ): GithubMetricsResponse
}
