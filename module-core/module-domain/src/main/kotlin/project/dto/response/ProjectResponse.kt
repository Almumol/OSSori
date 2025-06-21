package com.almumol.ossori.project.dto.response

import com.almumol.ossori.project.domain.Project

data class ProjectResponse(
        val id: Long,
        val name: String,
        val description: String,
        val githubLink: String,
        val countingStar: Long,
        val issueCount: Long,
        val contributionGuideKey: String? = null,
        val issueFrequency: Double,
        val timeToMerge: Double,
        val pullRequestFrequency: Double,
        val uniqueContributors: Long,
        val starDifference: Long,
        val firstResponseTimeOfPullRequest: Double,
) {

    companion object {
        fun from(project: Project): ProjectResponse = with(project) {
            ProjectResponse(
                    id,
                    name,
                    description,
                    githubLink,
                    countingStar,
                    issueCount,
                    contributionGuideKey,
                    issueFrequency,
                    timeToMerge,
                    pullRequestFrequency,
                    uniqueContributors,
                    starDifference,
                    firstResponseTimeOfPullRequest
            )
        }
    }
}
