package almumol.ossori.core.domain.project.service

import almumol.ossori.core.domain.project.domain.Project
import almumol.ossori.core.domain.project.dto.response.ProjectResponse
import almumol.ossori.core.domain.project.dto.response.ProjectsResponse
import almumol.ossori.core.domain.project.exception.BadRequestException
import almumol.ossori.core.domain.project.repository.ProjectRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository
) {

    fun findProjectById(projectId: Long): ProjectResponse {
        val project = projectRepository.findById(projectId)
            .orElseThrow { BadRequestException("존재하지 않는 프로젝트입니다.") }

        return ProjectResponse.from(project)
    }

    fun findAllProjects(pageable: Pageable): ProjectsResponse {
        val projects = projectRepository.findAll(pageable);

        return ProjectsResponse.from(projects);
    }

    fun hasProjectLessThan(count: Long): Boolean {
        val projectCount = projectRepository.count()
        return projectCount < count;
    }

    fun registerProject(project: Project) {
        if (projectRepository.existsProjectByOwner(project.owner)) {
            throw BadRequestException("이미 존재하는 Organization 입니다.")
        }
        projectRepository.save(project)
    }
}
