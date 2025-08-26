package almumol.ossori.core.client.github

import almumol.ossori.core.client.dto.response.*
import almumol.ossori.core.client.global.exception.GithubResponseException
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
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
        private const val OFFSET_OF_MONTH = 3L;
    }

    private val since: String
        get() = LocalDate.now().minusMonths(OFFSET_OF_MONTH).toString()

    fun getRepositories(filterQuery: String): GithubRepositoriesResponse {
        val uri = "${githubClientProperties.searchBaseUrl}/repositories$filterQuery"
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

    //PAT 를 사용해야 정상적으로 동작하고, 사용해도 분 당 10회라는 낮은 사용량이 제공됨
    fun getContentLocation(repositoryOwner: String, repositoryName: String, content: String): GithubSearchResponse {
        val filterQuery = "?q=repo:$repositoryOwner/$repositoryName+filename:$content"
        val uri = "${githubClientProperties.searchBaseUrl}/code$filterQuery"
        return getFromGithub(uri)
    }

    fun getContent(repositoryOwner: String, repositoryName: String, contentLocation: String): GithubContentResponse {
        val uri =
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/$contentLocation"
        return getFromGithub(uri)
    }

    fun getContributing(repositoryOwner: String, repositoryName: String): GithubContentResponse {
        val uris = listOf(
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/CONTRIBUTING.md",
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/.github/CONTRIBUTING.md",
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/docs/CONTRIBUTING.md",
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/contributing.md",
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/.github/contributing.md",
            "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName/contents/docs/contributing.md"
        )

        for (uri in uris) {
            try {
                return getFromGithub(uri)
            } catch (e: GithubResponseException) {
                if (e.statusCode != HttpStatus.NOT_FOUND) {
                    throw e
                }
            }
        }

        throw GithubResponseException(HttpStatus.NOT_FOUND, "CONTRIBUTING.md not found in repository $repositoryOwner/$repositoryName")
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
