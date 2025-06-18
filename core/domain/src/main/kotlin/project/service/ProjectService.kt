package com.almumol.ossori.project.service

import com.almumol.ossori.global.exception.BadRequestException
import com.almumol.ossori.project.dto.response.ProjectResponse
import com.almumol.ossori.project.repository.ProjectRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProjectService(
        private val projectRepository: ProjectRepository
) {

    @Transactional
    fun findProjectById(projectId: Long): ProjectResponse {
        val project = projectRepository.findById(projectId)
                .orElseThrow { BadRequestException("존재하지 않는 프로젝트입니다.") }

        return ProjectResponse.from(project)
    }
}
