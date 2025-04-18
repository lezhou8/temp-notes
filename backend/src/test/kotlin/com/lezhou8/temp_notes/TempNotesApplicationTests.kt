package com.lezhou8.temp_notes

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID
import java.time.Instant
import java.time.Duration
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@AutoConfigureWebTestClient
@Import(PostgresTestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TempNotesApplicationTests(@Autowired var webTestClient: WebTestClient) {

	@Test
	fun `create note, get note with uuid, write to note, get note with new content`() {
		// basic POST request, save the note
		val postNote = webTestClient
			.post()
			.uri("/api/note")
			.exchange()
			.expectStatus().isCreated
			.expectBody(Note::class.java)
			.returnResult()
			.responseBody
				?: throw IllegalStateException("No response body")

		// make sure all the fields look correct
		assertNotNull(postNote.uuid, "UUID should not be null")
		assertEquals("", postNote.content, "Content should be empty")
		assertTrue(postNote.expiry.isAfter(Instant.now()), "Expiry should be in the future")

		// basic GET request using the UUID we got from the POST request
		val getNote = webTestClient
			.get()
			.uri("/api/note/${postNote.uuid}")
			.exchange()
			.expectStatus().isOk
			.expectBody(Note::class.java)
			.returnResult()
			.responseBody
				?: throw IllegalStateException("No response body")

		// compare the two notes we have; they should be the same
		assertEquals(postNote.uuid, getNote.uuid, "We should get the same note")
		assertEquals(postNote.content, getNote.content, "We should get the same note")
		assertTrue(Duration.between(postNote.expiry, getNote.expiry).abs() < Duration.ofSeconds(1), "Roughly the same expiry")

		// update the content of the note
		val newContent: String = "new content"
		webTestClient
			.patch()
			.uri("/api/note/${getNote.uuid}")
			.bodyValue(mapOf("content" to newContent))
			.exchange()
			.expectStatus().isNoContent

		// get the updated note
		val updatedNote = webTestClient
			.get()
			.uri("/api/note/${getNote.uuid}")
			.exchange()
			.expectStatus().isOk
			.expectBody(Note::class.java)
			.returnResult()
			.responseBody
				?: throw IllegalStateException("No response body")

		// make sure the update is durable
		assertEquals(getNote.uuid, updatedNote.uuid, "The UUID should remain the same")
		assertTrue(Duration.between(getNote.expiry, updatedNote.expiry).abs() < Duration.ofSeconds(1), "Roughly the same expiry")
		assertEquals(updatedNote.content, newContent, "Make sure the update is durable")
	}

	@Test
	fun `invalid GET request due to invalid UUID`() {
		webTestClient
			.get()
			.uri("/api/note/invalid-uuid")
			.exchange()
			.expectStatus().isBadRequest
	}

	@Test
	fun `GET request with UUID that is probably not in the database`() {
		webTestClient
			.get()
			.uri("/api/note/00000000-0000-0000-0000-000000000000")
			.exchange()
			.expectStatus().isNotFound
	}

	@Test
	fun `invalid PATCH request due to invalid UUID`() {
		webTestClient
			.patch()
			.uri("/api/note/invalid-uuid")
			.exchange()
			.expectStatus().isBadRequest
	}

	@Test
	fun `invalid PATCH request due to invalid request body`() {
		val postNote = webTestClient
			.post()
			.uri("/api/note")
			.exchange()
			.expectStatus().isCreated
			.expectBody(Note::class.java)
			.returnResult()
			.responseBody
				?: throw IllegalStateException("No response body")

		webTestClient
			.patch()
			.uri("/api/note/${postNote.uuid}")
			.bodyValue(mapOf("incorrect key" to "value"))
			.exchange()
			.expectStatus().isBadRequest
	}
}
