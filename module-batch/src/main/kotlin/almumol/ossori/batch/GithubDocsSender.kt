package almumol.ossori.batch

import almumol.ossori.core.client.aws.S3DocsClient
import almumol.ossori.core.client.dto.response.GithubContentResponse
import almumol.ossori.core.domain.project.domain.Project
import org.springframework.stereotype.Component

@Component
class GithubDocsSender(
    private val s3DocsClient: S3DocsClient,
) {
    fun saveToBucket(project: Project, githubContentResponse: GithubContentResponse): String? {
        if (project.hasSameContents(githubContentResponse.sha)) {
            return project.contributionGuideKey
        }
        return s3DocsClient.updateToS3(
            project.owner,
            project.name,
            githubContentResponse.name,
            githubContentResponse.content
        )
    }
}
