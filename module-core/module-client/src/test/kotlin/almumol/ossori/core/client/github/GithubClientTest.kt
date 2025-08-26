package almumol.ossori.core.client.github

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
}
