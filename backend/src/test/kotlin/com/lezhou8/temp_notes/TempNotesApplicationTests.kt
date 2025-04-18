package com.lezhou8.temp_notes

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@Import(PostgresTestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TempNotesApplicationTests(@Autowired var webTestClient: WebTestClient) {

	@Test
	fun contextLoads() {}
}
