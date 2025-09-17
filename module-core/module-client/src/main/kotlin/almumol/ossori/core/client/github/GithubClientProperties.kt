package almumol.ossori.core.client.github

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "security.github")
data class GithubClientProperties(

    val repositoryBaseUrl: String,

    val searchBaseUrl: String,

    val token: String
) {
}
