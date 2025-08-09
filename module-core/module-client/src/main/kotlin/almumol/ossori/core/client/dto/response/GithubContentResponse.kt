package almumol.ossori.core.client.dto.response

data class GithubContentResponse (
    val name: String,
    val sha: String,
    val size: Long,
    val downloadUrl: String?,
    val content: String?,
    val encoding: String?,
)
