package client.github

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "security.github")
data class GithubClientProperties(

    val repositoryBaseUrl: String,

    val searchRepositoryBaseUrl: String,

    val token: String
) {
}
