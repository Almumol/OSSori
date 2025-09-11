package almumol.ossori.core.client.aws

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud.aws.credentials")
data class AwsCredentialsProperties(

    val accessKey: String,

    val secretAccessKey: String,
)
