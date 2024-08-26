package surport.supportServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class SupportServerApplication

fun main(args: Array<String>) {
	runApplication<SupportServerApplication>(*args)
}