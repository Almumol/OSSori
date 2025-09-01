package almumol.ossori.batch

import almumol.ossori.core.client.dto.response.GithubContentResponse
import almumol.ossori.core.domain.project.domain.Project
import org.springframework.stereotype.Component

@Component
class GithubReadMeSender {
    fun saveToBucket(project: Project, githubContentResponse: GithubContentResponse): String? {
        if (project.hasSameContents(githubContentResponse.sha)) {
            return project.contributionGuideKey;
        }

        // bucket api 호출
        throw NotImplementedError("This method is not implemented yet.")
    }
}
