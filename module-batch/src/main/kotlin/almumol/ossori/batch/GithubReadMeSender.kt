package almumol.ossori.batch

import almumol.ossori.core.client.dto.response.GithubContentResponse
import org.springframework.stereotype.Component

@Component
abstract class GithubReadMeSender {
    abstract fun saveToBucket(githubContentResponse: GithubContentResponse): String
}
