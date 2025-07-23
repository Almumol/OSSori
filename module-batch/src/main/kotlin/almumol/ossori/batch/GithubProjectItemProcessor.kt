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
    private val githubReadMeSender: GithubReadMeSender
) : ItemProcessor<Project, Project> {

    override fun process(previousProject: Project): Project? {
        val commits = githubClient.getCommits(previousProject.owner, previousProject.name)
        val pullRequests = githubClient.getPullRequests(previousProject.owner, previousProject.name)
        val contributors = githubClient.getContributors(previousProject.owner, previousProject.name)
        val repositorySummary = githubClient.getRepository(previousProject.owner, previousProject.name)
        val readMe = githubClient.getReadMe(previousProject.owner, previousProject.name)

        val contributionGuideKey = githubReadMeSender.saveToBucket(readMe)
        val calculateMetrics = githubMetricsCalculator.calculateMetrics(repositorySummary, commits, pullRequests, contributors)

        return previousProject.copy(
            countingStar = repositorySummary.stargazersCount,
            issueCount = repositorySummary.openIssuesCount,
            contributionGuideKey = contributionGuideKey,
            issueFrequency = calculateMetrics.issueCreationRate,
            timeToMerge = calculateMetrics.averageMergeTime,
            pullRequestFrequency = calculateMetrics.pullRequestCreationRate,
            uniqueContributors = contributors.size.toLong(),
            starDifference = calculateMetrics.starDifference,
            firstResponseTimeOfPullRequest = calculateMetrics.pullRequestAverageFirstResponseTime
        )
    }
}
