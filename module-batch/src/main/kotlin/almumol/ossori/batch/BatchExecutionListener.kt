package almumol.ossori.batch

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.stereotype.Component

@Slf4j
@Component
class BatchExecutionListener : JobExecutionListener, StepExecutionListener {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun beforeJob(jobExecution: JobExecution) {
        logger.info("Job ${jobExecution.jobInstance.jobName} 시작")
    }

    override fun afterJob(jobExecution: JobExecution) {
        logger.info("Job ${jobExecution.jobInstance.jobName} 종료. 상태: ${jobExecution.exitStatus}")
    }

    override fun beforeStep(stepExecution: StepExecution) {
        logger.info("Step ${stepExecution.stepName} 시작")
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus? {
        logger.info("Step ${stepExecution.stepName} 종료. 상태: ${stepExecution.exitStatus}")
        return null
    }
}
