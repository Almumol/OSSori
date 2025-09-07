package almumol.ossori.core.client.global.exception

import org.springframework.http.HttpStatusCode

class GithubResponseException(
    val statusCode: HttpStatusCode,
    message: String
) : RuntimeException(
    "Status code: ${statusCode.value()}, Description: $message"
)
