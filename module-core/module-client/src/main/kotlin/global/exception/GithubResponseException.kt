package global.exception

import org.springframework.http.HttpStatusCode

class GithubResponseException(statusCode: HttpStatusCode, message: String) : RuntimeException(
    "Status code: ${statusCode}, Description: $message"
)
