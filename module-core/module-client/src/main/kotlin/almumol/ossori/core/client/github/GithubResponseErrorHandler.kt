package almumol.ossori.core.client.github

import almumol.ossori.core.client.global.exception.GithubResponseException
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import java.net.URI

class GithubResponseErrorHandler : ResponseErrorHandler {

    companion object {
        private const val GITHUB_SERVER_ERROR = "Github Server Unavailable"
    }

    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.is4xxClientError || response.statusCode.is5xxServerError
    }

    override fun handleError(url: URI, method: HttpMethod, response: ClientHttpResponse) {
        val body = response.body.bufferedReader().readText()
        when {
            response.statusCode.is4xxClientError ->
                throw GithubResponseException(response.statusCode, body)
            response.statusCode.is5xxServerError ->
                throw GithubResponseException(response.statusCode, GITHUB_SERVER_ERROR)
            else ->
                throw GithubResponseException(response.statusCode, body)
        }
    }
}
