package almumol.ossori.core.client.github

import almumol.ossori.core.domain.project.domain.Project
import almumol.ossori.core.domain.project.service.ProjectService
import jakarta.annotation.PostConstruct
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class GithubProjectCollector(
    private val githubClient: GithubClient,
    private val projectService: ProjectService,
) {
    companion object {
        private const val TOTAL_DUMMY_PROJECT_COUNT: Long = 300L
        private const val PROJECT_COUNT_PER_REQUEST: Long = 100L
    }

    @PostConstruct
    fun init() {
        if (projectService.hasProjectLessThan(TOTAL_DUMMY_PROJECT_COUNT)) {
            fetchAndSaveTopProjects()
        }
    }

    fun fetchAndSaveTopProjects() {
        for (page in 1..(TOTAL_DUMMY_PROJECT_COUNT / PROJECT_COUNT_PER_REQUEST)) {
            // GitHub Search API 필터
            // stars:>1000 => 1000개 이상 스타
            // sort=stars&order=desc => 인기순
            val query = String.format("?q=stars:>1000&sort=stars&order=desc&per_page=%d&page=%d", PROJECT_COUNT_PER_REQUEST, page)
            val response = githubClient!!.getRepositories(query)
            response.items.forEach { repo ->
                val project = Project(
                    id = 0L,
                    name = repo.name,
                    description = repo.description,
                    owner = repo.owner.login,
                    githubLink = repo.htmlUrl,
                    countingStar = repo.stargazersCount,
                    issueCount = repo.openIssuesCount,
                    contributionGuideKey = null,
                    issueFrequency = 0.0,
                    timeToMerge = 0.0,
                    pullRequestFrequency = 0.0,
                    uniqueContributors = 0L,
                    starDifference = 0L,
                    firstResponseTimeOfPullRequest = 0.0
                )
                projectService.registerProject(project)
            }
        }
    }
}

