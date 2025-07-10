package client.github

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.Test

@TestConfiguration
@ComponentScan(basePackages = ["client", "config"])
class TestConfig() {
}

@TestPropertySource(
    properties = [
        "security.github.repository_base_url=https://api.github.com/repos/",
        "security.github.search_repository_base_url=https://api.github.com/search/repositories",
        "security.github.token=dummy-token"
    ]
)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TestConfig::class])
class GithubClientTest(

    @Autowired
    val githubClient: GithubClient
) {

    @Disabled
    @Test
    fun repositoryTest() {
        val repositoryInfo = githubClient.getRepositoryInfo("Almumol", "OSSori")
        assertNotNull(repositoryInfo)
    }

    @Disabled
    @Test
    fun searchRepositoryTest() {
        var openSourceRepository = githubClient.getOpenSourceRepository("?q=good-first-issues:>0")
        assertNotNull(openSourceRepository)
        openSourceRepository = githubClient.getOpenSourceRepository("?q=help-wanted-issues:>0")
        assertNotNull(openSourceRepository)
    }
}
