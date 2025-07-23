package almumol.ossori.core.client.github

import almumol.ossori.core.client.dto.response.*
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.MediaType.APPLICATION_JSON
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
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val AUTHORIZATION_METHOD = "Bearer"
    }

    val offsetOfMonth = 3L;
    val since = LocalDate.now().minusMonths(offsetOfMonth).toString()

    fun getRepositories(filterQuery: String): GithubRepositoriesResponse {
        val uri = "${githubClientProperties.searchRepositoryBaseUrl}$filterQuery"
        return defaultGithubGetClient(uri)//?q=good-first-issues:>1+help-wanted-issues:>1
    }

    fun getRepository(repositoryOwner: String, repositoryName: String): GithubRepositoryResponse {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName"
        return defaultGithubGetClient(uri)
    }

    fun getPullRequests(repositoryOwner: String, repositoryName: String): List<GithubPullRequestResponse> {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/pulls?state=all&since=" + since
        return defaultGithubGetClient(uri)
    }

    fun getCommits(repositoryOwner: String, repositoryName: String): List<GithubCommitResponse> {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/commits?since=" + since
        return defaultGithubGetClient(uri)
    }

    fun getContributors(repositoryOwner: String, repositoryName: String): List<GithubContributorResponse> {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contributors"
        return defaultGithubGetClient(uri)
    }

    private fun getGithubToken(): String =
        "$AUTHORIZATION_METHOD ${githubClientProperties.token}"

    private inline fun <reified T> defaultGithubGetClient(uri: String): T {
        return githubRestClient.get()
            .uri(uri)
            .accept(APPLICATION_JSON)
            //.header(AUTHORIZATION_HEADER, getGithubToken())
            .retrieve()
            .body(T::class.java)
            ?: throw RuntimeException("Null response from $uri")
    }
}
