//import org.springframework.batch.core.Job
//import org.springframework.batch.core.Step
//import org.springframework.batch.core.job.builder.JobBuilder
//import org.springframework.batch.core.step.builder.JobStepBuilder
//import org.springframework.batch.core.step.builder.StepBuilder
//import org.springframework.batch.repeat.RepeatStatus
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//
//@Configuration
//class BatchConfiguration {
//    private val jobBuilderFactory: JobBuilder? = null
//    private val stepBuilderFactory: StepBuilder? = null
//
//    @Bean
//    fun job(): Job {
//        return jobBuilderFactory.get("simpleJob")
//            .start(simpleStep1())
//            .build()
//    }
//
//    @Bean
//    fun step(): Step {
//        return stepBuilderFactory.get("simpleStep1")
//            .tasklet({ contribution, chunkContext ->
//                RepeatStatus.FINISHED
//            })
//            .build()
//    }
//
//}
