package almumol.ossori.core.client.aws

import almumol.ossori.core.client.github.TestConfig
import almumol.ossori.core.domain.project.service.ProjectService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestConfiguration
@ComponentScan(basePackages = ["almumol.ossori.core.client"])
class TestConfig() {
}

@TestPropertySource(
    properties = [
        "security.github.repository_base_url=https://api.github.com/repos/",
        "security.github.search_base_url=https://api.github.com/search",
        "security.github.token=test-pat",
        "cloud.aws.credentials.access-key=access-key",
        "cloud.aws.credentials.secret-access-key=secret-access-key",
        "cloud.aws.s3.bucket-name=ossori"
    ]
)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TestConfig::class])
class S3DocsClientTest(

    @Autowired
    val s3DocsClient: S3DocsClient,
) {
    companion object {
        private const val TEST_ONWER = "Almumol"
        private const val TEST_NAME = "OSSori"
        private const val TEST_DOCS = "contributing.md"
        private const val TEST_CONTENT = "content"
    }

    @MockitoBean
    lateinit var projectService: ProjectService

    @Disabled
    @Test
    fun updateToS3() {
        val key = s3DocsClient.updateToS3(TEST_ONWER, TEST_NAME, TEST_DOCS, TEST_CONTENT)
        //정상적으로 동작하려면 accessKey, secretAccessKey를 사용해야 함
        assertEquals("docs/$TEST_ONWER/$TEST_NAME/CONTRIBUTING.md", key)
    }
}
