package almumol.ossori.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class GithubJobScheduler(
    private val jobLauncher: JobLauncher,
    private val githubRefreshJob: Job
) {

    @Scheduled(cron = "0 0 1 * * ?")
    fun runGithubRefreshJob() {
        try {
            val jobParameters = JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters()
            jobLauncher.run(githubRefreshJob, jobParameters)
        } catch (e: Exception) {
            println("GitHub 배치 실행 중 오류: ${e.message}")
            e.printStackTrace()
        }
    }
}
