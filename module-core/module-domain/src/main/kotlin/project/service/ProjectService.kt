package com.almumol.ossori.project.service

import ProjectsResponse
import com.almumol.ossori.global.exception.BadRequestException
import com.almumol.ossori.project.dto.response.ProjectResponse
import com.almumol.ossori.project.repository.ProjectRepository
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
}
