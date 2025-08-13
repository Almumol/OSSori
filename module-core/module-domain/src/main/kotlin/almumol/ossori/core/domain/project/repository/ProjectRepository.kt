package almumol.ossori.core.domain.project.repository

import almumol.ossori.core.domain.project.domain.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun existsProjectByOwner(name: String): Boolean
}
