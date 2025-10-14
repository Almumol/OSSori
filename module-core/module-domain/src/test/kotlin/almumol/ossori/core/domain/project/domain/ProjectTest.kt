package almumol.ossori.core.domain.project.domain

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProjectTest {

    @Test
    @DisplayName("CONTRIBUTING.md 저장 시 같은 SHA면 동일한 내용으로 간주")
    fun hasSameContents() {
        Project(
            id = 1,
            owner = "owner",
            name = "name",
            description = "description",
            githubLink = "githubLink",
            countingStar = 100,
            issueCount = 10,
            contributionGuideKey = "key",
            contributionGuideSha = "sha123",
            issueFrequency = 1.0,
            timeToMerge = 2.0,
            pullRequestFrequency = 3.0,
            uniqueContributors = 5,
            starDifference = 20,
            firstResponseTimeOfPullRequest = 4.0
        ).let { project ->
            assertTrue(project.hasSameContents("sha123"))
            assertFalse(project.hasSameContents("differentSha"))
        }
    }
}
