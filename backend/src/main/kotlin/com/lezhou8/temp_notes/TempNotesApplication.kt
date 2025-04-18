package com.lezhou8.temp_notes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TempNotesApplication

fun main(args: Array<String>) {
	runApplication<TempNotesApplication>(*args)
}
