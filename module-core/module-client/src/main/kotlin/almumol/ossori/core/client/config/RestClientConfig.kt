package almumol.ossori.core.client.config

import almumol.ossori.core.client.github.GithubResponseErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.time.Duration

@Configuration
class RestClientConfig {

    @Bean
    fun githubRestClient(): RestClient {
        val requestFactory = simpleRequestFactory()
        return RestClient.builder()
            .requestFactory(requestFactory)
            .defaultStatusHandler(GithubResponseErrorHandler())
            .build()
    }

    private fun simpleRequestFactory(): ClientHttpRequestFactory {
        val requestFactory = SimpleClientHttpRequestFactory()
        requestFactory.setConnectTimeout(Duration.ofSeconds(2))
        requestFactory.setReadTimeout(Duration.ofSeconds(10))

        return requestFactory
    }
}
