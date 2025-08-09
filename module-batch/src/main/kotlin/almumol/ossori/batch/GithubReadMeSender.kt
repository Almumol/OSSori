package almumol.ossori.batch

import almumol.ossori.core.client.dto.response.GithubContentResponse
import org.springframework.stereotype.Component

@Component
class GithubReadMeSender {
    fun saveToBucket(githubContentResponse: GithubContentResponse): String {
        throw NotImplementedError("This method is not implemented yet.")
    }
}
