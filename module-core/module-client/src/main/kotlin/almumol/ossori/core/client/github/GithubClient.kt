package almumol.ossori.core.client.github

import almumol.ossori.core.client.dto.response.*
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.LocalDate

@Component
@EnableConfigurationProperties(GithubClientProperties::class)
class GithubClient(
    private val githubRestClient: RestClient,
    private val githubClientProperties: GithubClientProperties
) {
    companion object {
        private const val GITHUB_API_MEDIA_TYPE = "application/vnd.github+json"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val AUTHORIZATION_METHOD = "Bearer"
    }

    val offsetOfMonth = 3L;
    val since = LocalDate.now().minusMonths(offsetOfMonth).toString()

    fun getRepositories(filterQuery: String): GithubRepositoriesResponse {
        val uri = "${githubClientProperties.searchRepositoryBaseUrl}$filterQuery"
        return getFromGithub(uri)//?q=good-first-issues:>1+help-wanted-issues:>1
    }

    fun getRepository(repositoryOwner: String, repositoryName: String): GithubRepositoryResponse {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName"
        return getFromGithub(uri)
    }

    fun getPullRequests(repositoryOwner: String, repositoryName: String): List<GithubPullRequestResponse> {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/pulls?state=all&since=" + since
        return getFromGithub(uri)
    }

    fun getCommits(repositoryOwner: String, repositoryName: String): List<GithubCommitResponse> {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/commits?since=" + since
        return getFromGithub(uri)
    }

    fun getContributors(repositoryOwner: String, repositoryName: String): List<GithubContributorResponse> {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contributors"
        return getFromGithub(uri)
    }

    fun getReadMe(repositoryOwner: String, repositoryName: String): GithubContentResponse {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/readme"
        return getFromGithub(uri)
    }

    private fun getGithubToken(): String =
        "$AUTHORIZATION_METHOD ${githubClientProperties.token}"

    private inline fun <reified T> getFromGithub(uri: String): T {
        return githubRestClient.get()
            .uri(uri)
            .header(HttpHeaders.ACCEPT, GITHUB_API_MEDIA_TYPE)
            //.header(AUTHORIZATION_HEADER, getGithubToken())
            .retrieve()
            .body(T::class.java)
            ?: throw RuntimeException("Null response from $uri")
    }
}
