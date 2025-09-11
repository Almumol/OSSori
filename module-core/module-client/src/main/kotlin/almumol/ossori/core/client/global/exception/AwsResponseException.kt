package almumol.ossori.core.client.global.exception

import org.springframework.http.HttpStatusCode

class AwsResponseException(
    val statusCode: HttpStatusCode,
    message: String
) : RuntimeException(
    "Status code: ${statusCode.value()}, Description: $message"
)
