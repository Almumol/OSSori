package almumol.ossori.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["almumol"])
@EnableJpaRepositories(basePackages = ["almumol.ossori.core.domain"])
@EntityScan(basePackages = ["almumol.ossori.core.domain"])
class OssoriApplication

fun main(args: Array<String>) {
    runApplication<OssoriApplication>(*args)
}
