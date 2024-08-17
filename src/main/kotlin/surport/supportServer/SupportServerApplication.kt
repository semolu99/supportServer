package surport.supportServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SupportServerApplication

fun main(args: Array<String>) {
	runApplication<SupportServerApplication>(*args)
}