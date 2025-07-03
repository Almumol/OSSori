import com.almumol.ossori.project.domain.Project
import com.almumol.ossori.project.dto.response.ProjectResponse
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
