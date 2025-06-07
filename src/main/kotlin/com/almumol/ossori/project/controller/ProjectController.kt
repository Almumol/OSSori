package com.almumol.ossori.project.controller

import com.almumol.ossori.project.dto.response.ProjectResponse
import com.almumol.ossori.project.service.ProjectService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
class ProjectController(
        private val projectService: ProjectService
) {

    @GetMapping("/{id}")
    fun findProject(@PathVariable id: Long): ProjectResponse =
            projectService.findProjectById(id)
}
