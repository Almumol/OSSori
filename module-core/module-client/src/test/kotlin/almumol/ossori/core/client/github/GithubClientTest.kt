package almumol.ossori.core.client.github

import almumol.ossori.core.domain.project.service.ProjectService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.Test

@TestConfiguration
@ComponentScan(basePackages = ["almumol.ossori.core.client"])
class TestConfig() {
}

@TestPropertySource(
    properties = [
        "security.github.repository_base_url=https://api.github.com/repos/",
        "security.github.search_base_url=https://api.github.com/search",
        "security.github.token=dummy-token"
    ]
)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TestConfig::class])
class GithubClientTest(

    @Autowired
    val githubClient: GithubClient
) {

    @MockitoBean
    lateinit var projectService: ProjectService

    @Disabled
    @Test
    fun repositoryTest() {
        val repositoryInfo = githubClient.getRepository("Almumol", "OSSori")
        assertNotNull(repositoryInfo)
    }

    @Disabled
    @Test
    fun searchRepositoryTest() {
        var openSourceRepository = githubClient.getRepositories("?q=good-first-issues:>0")
        assertNotNull(openSourceRepository)
        openSourceRepository = githubClient.getRepositories("?q=help-wanted-issues:>0")
        assertNotNull(openSourceRepository)
    }

    @Disabled
    @Test
    fun searchContentLocationTest() {
        val contributingLocation = githubClient.getContentLocation("spring-projects", "spring-framework", "CONTRIBUTING.md")
        //정상적으로 동작하려면 PAT를 사용해야 함
        assertNotNull(contributingLocation)
        assertEquals("CONTRIBUTING.md", contributingLocation.items.first().path)
    }

    @Disabled
    @Test
    fun searchContentTest() {
        val contentResponse = githubClient.getContent("spring-projects", "spring-framework", "CONTRIBUTING.md")
        assertNotNull(contentResponse)
        assertEquals("CONTRIBUTING.md", contentResponse.name)
        assertEquals("CONTRIBUTING.md", contentResponse.path)
    }

    @Disabled
    @Test
    fun getContributingTest() {
        var contentResponse = githubClient.getContributing("spring-projects", "spring-framework")
        assertEquals("CONTRIBUTING.md", contentResponse.name)
        contentResponse = githubClient.getContributing("google", "syzkaller")
        assertEquals("docs/contributing.md", contentResponse.path)
    }
}
