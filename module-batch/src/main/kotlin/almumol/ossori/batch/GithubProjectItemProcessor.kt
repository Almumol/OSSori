package almumol.ossori.batch

import almumol.ossori.core.client.github.GithubClient
import almumol.ossori.core.client.github.GithubMetricsCalculator
import almumol.ossori.core.domain.project.domain.Project
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class GithubProjectItemProcessor(
    private val githubClient: GithubClient,
    private val githubMetricsCalculator: GithubMetricsCalculator,
    private val githubDocsSender: GithubDocsSender,
) : ItemProcessor<Project, Project> {

    override fun process(previousProject: Project): Project? {
        val commits = githubClient.getCommits(previousProject.owner, previousProject.name)
        val pullRequests = githubClient.getPullRequests(previousProject.owner, previousProject.name)
        val contributors = githubClient.getContributors(previousProject.owner, previousProject.name)
        val repositorySummary = githubClient.getRepository(previousProject.owner, previousProject.name)
        val readMe = githubClient.getReadMe(previousProject.owner, previousProject.name)

        val contributionGuideKey = githubDocsSender.saveToBucket(previousProject, readMe)
        val calculatedMetrics = githubMetricsCalculator.calculateMetrics(repositorySummary, commits, pullRequests, contributors)

        return previousProject.copy(
            countingStar = repositorySummary.stargazersCount,
            issueCount = repositorySummary.openIssuesCount,
            contributionGuideKey = contributionGuideKey,
            contributionGuideSha = readMe.sha,
            issueFrequency = calculatedMetrics.issueCreationRate,
            timeToMerge = calculatedMetrics.averageMergeTime,
            pullRequestFrequency = calculatedMetrics.pullRequestCreationRate,
            uniqueContributors = contributors.size.toLong(),
            starDifference = calculatedMetrics.starDifference,
            firstResponseTimeOfPullRequest = calculatedMetrics.pullRequestAverageFirstResponseTime
        )
    }
}
