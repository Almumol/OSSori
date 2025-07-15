package almumol.ossori.core.domain.project.dto.response

import almumol.ossori.core.domain.project.domain.Project
import org.springframework.data.domain.Page

data class ProjectsResponse(
    val projects: List<ProjectResponse>,
    val size: Int,
    val number: Int
) {
    companion object {
        fun from(projects: Page<Project>): ProjectsResponse =
            ProjectsResponse(
                projects = projects.content.map { project -> ProjectResponse.from(project) },
                size = projects.size,
                number = projects.number
            )
    }
}
