package almumol.ossori.batch

import almumol.ossori.core.client.github.GithubClient
import almumol.ossori.core.client.github.GithubMetricsCalculator
import almumol.ossori.core.domain.project.domain.Project
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class GithubBatchConfiguration(
    val githubClient: GithubClient,
    val githubMetricsCalculator: GithubMetricsCalculator,
    val githubReadMeSender: GithubReadMeSender,
    val batchExecutionListener: BatchExecutionListener
) {

    @Bean
    fun githubRefreshJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        entityManagerFactory: EntityManagerFactory
    ): Job {
        return JobBuilder("githubRefreshJob", jobRepository)
            .start(projectRefreshStep(jobRepository, transactionManager, entityManagerFactory))
            .listener(batchExecutionListener)
            .build()
    }

    @Bean
    fun projectRefreshStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        entityManagerFactory: EntityManagerFactory
    ): Step {
        return StepBuilder("projectRefreshStep", jobRepository)
            .chunk<Project, Project>(30, transactionManager)
            .reader(projectItemReader(entityManagerFactory))
            .processor(projectItemProcessor())
            .writer(projectItemWriter(entityManagerFactory))
            .listener(batchExecutionListener)
            .build()
    }

    @Bean
    fun projectItemProcessor(): ItemProcessor<Project, Project> {
        return GithubProjectItemProcessor(githubClient, githubMetricsCalculator, githubReadMeSender)
    }

    @Bean
    fun projectItemReader(entityManagerFactory: EntityManagerFactory): JpaPagingItemReader<Project> {
        return JpaPagingItemReaderBuilder<Project>()
            .name("projectReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT p FROM Project p")
            .pageSize(30)
            .build()
    }

    @Bean
    fun projectItemWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<Project> {
        return JpaItemWriter<Project>().apply {
            setEntityManagerFactory(entityManagerFactory)
            setUsePersist(false)
        }
    }
}
