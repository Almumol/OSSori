package almumol.ossori.core.client.aws

import almumol.ossori.core.client.global.exception.AwsResponseException
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception

@EnableConfigurationProperties(AwsS3Properties::class)
@Component
class S3DocsClient(
    private val s3Client: S3Client,
    private val awsS3Properties: AwsS3Properties,
) {
    companion object {
        private const val MARKDOWN: String = "text/markdown"
        private const val MARKDOWN_EXTENSION: String = ".md"
        private const val DOCS_KEY_PREFIX: String = "docs/"
    }

    fun updateToS3(projectOwner: String, projectName: String, docsName: String, content: String): String {
        val key = buildKey(projectOwner, projectName, docsName)
        val putRequest: PutObjectRequest = PutObjectRequest.builder().apply {
            bucket(awsS3Properties.bucketName)
            key(key)
            contentType(MARKDOWN)
        }.build()

        return handleS3Exception {
            s3Client.putObject(putRequest, RequestBody.fromString(content, Charsets.UTF_8))
            key
        }
    }

    fun downloadFromS3(projectOwner: String, projectName: String, contentName: String): String {
        val key = buildKey(projectOwner, projectName, contentName)
        val getRequest: GetObjectRequest = GetObjectRequest.builder().apply {
            bucket(awsS3Properties.bucketName)
            key(key)
        }.build()

        return handleS3Exception {
            val bytes = s3Client.getObject(getRequest, ResponseTransformer.toBytes())
            bytes.asUtf8String()
        }
    }

    private fun buildKey(projectOwner: String, projectName: String, docsName: String): String {
        return "$DOCS_KEY_PREFIX${projectOwner}/${projectName}/${docsNameConverter(docsName)}"
    }

    private fun docsNameConverter(name: String): String {
        require(name.endsWith(MARKDOWN_EXTENSION)) { "Document's name must end with '.md'" }
        return name.substringBeforeLast(MARKDOWN_EXTENSION).uppercase() + MARKDOWN_EXTENSION
    }

    private inline fun <T> handleS3Exception(block: () -> T): T {
        return try {
            block()
        } catch (e: S3Exception) {
            throw AwsResponseException(HttpStatus.BAD_REQUEST, "S3Exception: ${e.message}")
        } catch (e: SdkException) {
            throw AwsResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "Aws Sdk Exception: ${e.message}")
        }
    }
}
