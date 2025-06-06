package com.almumol.ossori.project.repository

import com.almumol.ossori.project.domain.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
}
