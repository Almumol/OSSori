package almumol.ossori.core.client.config

import almumol.ossori.core.client.aws.AwsCredentialsProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client

@EnableConfigurationProperties(AwsCredentialsProperties::class)
@Configuration
class S3ClientConfig(
    private val awsCredentialsProperties: AwsCredentialsProperties,
) {

    @Bean
    fun s3Client(): S3Client {
        val credential: AwsCredentials =
            AwsBasicCredentials.create(awsCredentialsProperties.accessKey, awsCredentialsProperties.secretAccessKey)

        return S3Client.builder()
            .region(software.amazon.awssdk.regions.Region.AP_NORTHEAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(credential))
            .build()
    }
}
