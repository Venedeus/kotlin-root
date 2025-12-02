package dev.shvetsov.webfluxpostgresql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebfluxpostgresqlApplication

fun main(args: Array<String>) {
	runApplication<WebfluxpostgresqlApplication>(*args)
}
