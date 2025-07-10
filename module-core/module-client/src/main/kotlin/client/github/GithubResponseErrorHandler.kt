package client.github

import global.exception.GithubResponseException
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import java.net.URI

class GithubResponseErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.is4xxClientError || response.statusCode.is5xxServerError
    }

    override fun handleError(url: URI, method: HttpMethod, response: ClientHttpResponse) {
        if (response.statusCode.is4xxClientError) {
            throw GithubResponseException("Status code: ${response.statusCode}, Description: ${response.body}")
        }
        if (response.statusCode.is5xxServerError) {
            throw GithubResponseException("Github Server Unavailable")
        }
        throw GithubResponseException(response.body.toString())
    }
}
