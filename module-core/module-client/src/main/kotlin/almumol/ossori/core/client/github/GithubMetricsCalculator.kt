package almumol.ossori.core.client.github

import almumol.ossori.core.client.dto.response.*
import org.springframework.stereotype.Component

@Component
class GithubMetricsCalculator() {

    fun calculateMetrics(
        repositorySummary: GithubRepositoryResponse,
        commits: List<GithubCommitResponse>,
        pullRequests: List<GithubPullRequestResponse>,
        contributors: List<GithubContributorResponse>
    ): GithubMetricsResponse {
        throw NotImplementedError("This method is not implemented yet.")
    }
}
