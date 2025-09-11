package almumol.ossori.core.client.aws

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud.aws.s3")
data class AwsS3Properties(

    val bucketName: String,
)
