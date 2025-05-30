package com.almumol.ossori.project.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class Project(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(nullable = false, unique = true)
        val name: String,

        val description: String,

        @Column(nullable = false)
        val githubLink: String,

        @Column(nullable = false)
        val countingStar: Long,

        @Column(nullable = false)
        val issueCount: Long,

        // CONTRIBUTING.md
        val contributionGuide: String? = null,

        // 이슈 생성 빈도
        @Column(nullable = false)
        val issueFrequency: Double,

        // 머지 시간
        @Column(nullable = false)
        val timeToMerge: Double,

        // PR 생성 빈도
        @Column(nullable = false)
        val pullRequestFrequency: Double,

        // 고유한 기여자 수
        @Column(nullable = false)
        val uniqueContributors: Long,

        // 스타 변화량
        @Column(nullable = false)
        val starDifference: Long,

        // PR 최초 응답 시간
        @Column(nullable = false)
        val firstResponseTimeOfPullRequest: Double,
)
