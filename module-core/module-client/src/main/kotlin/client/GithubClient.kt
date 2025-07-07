package client

import com.almumol.ossori.client.GithubClientProperties
import dto.GithubRepositoriesResponse
import dto.GithubRepositoryResponse
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@EnableConfigurationProperties(GithubClientProperties::class)
@Component
class GithubClient(
    private val restClient: RestClient,
    private val githubClientProperties: GithubClientProperties
) {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val AUTHORIZATION_METHOD = "Bearer"
    }

    fun getOpenSourceRepository(filterQuery: String): GithubRepositoriesResponse {
        val uri = "${githubClientProperties.searchRepositoryBaseUrl}$filterQuery"
        return defaultGithubGetClient(uri)//?q=good-first-issues:>1+help-wanted-issues:>1
    }

    fun getRepositoryInfo(repositoryOwner: String, repositoryName: String): GithubRepositoryResponse {
        val uri = "${githubClientProperties.repositoryBaseUrl}/$repositoryOwner/$repositoryName"
        return defaultGithubGetClient(uri)
    }

    private fun getGithubToken(): String {
        return "$AUTHORIZATION_METHOD ${githubClientProperties.token}"
    }

    private inline fun <reified T> defaultGithubGetClient(uri: String): T {
        val response = restClient.get()
            .uri(uri)
            .accept(APPLICATION_JSON)
            //.header(AUTHORIZATION_HEADER, getGithubToken())
            .retrieve()
            .onStatus({ it.is4xxClientError }) { _, response ->
                throw RuntimeException("Status code: ${response.statusCode}, Description: ${response.body}")
            }
            .onStatus({ it.is5xxServerError }) { _, _ ->
                throw RuntimeException("Github Server Unavailable")
            }
            .body(T::class.java)
        return response ?: throw RuntimeException("Response body is null")
    }
}
