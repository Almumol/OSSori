package almumol.ossori.core.client.global.exception

import org.springframework.http.HttpStatusCode

class GithubResponseException(statusCode: HttpStatusCode, message: String) : RuntimeException(
    "Status code: ${statusCode}, Description: $message"
)
