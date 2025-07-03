import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
class BatchScheduler {

}



//   @Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Step sampleStep() {
//        return stepBuilderFactory.get("sampleStep")
//            .<String, String>chunk(10)
//            .reader(() -> "data")   // 간단한 Reader
//            .processor(item -> item.toUpperCase())  // Processor
//            .writer(items -> items.forEach(System.out::println))  // Writer
//            .build();
//    }
//
//    @Bean
//    public Job sampleJob() {
//        return jobBuilderFactory.get("sampleJob")
//            .start(sampleStep())
//            .build();
//    }
//}
