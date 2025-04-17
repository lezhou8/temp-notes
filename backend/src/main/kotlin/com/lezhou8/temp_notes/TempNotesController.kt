package com.lezhou8.temp_notes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.HttpStatus

import java.util.UUID

@RestController
@RequestMapping("api/note")
class TempNotesController(private val noteRepository: NoteRepository) {

	@GetMapping("/{uuid}")
	fun getNote(@PathVariable uuid: UUID): ResponseEntity<Any> {
		val note = noteRepository.findById(uuid)
		return if (note.isPresent)
			ResponseEntity.ok(note.get())
		else
			ResponseEntity.status(404).body("Did not find the UUID in database")
	}

	@PostMapping
	fun newNote(): ResponseEntity<Any> {
		val MAX_NOTES = 100L
		if (noteRepository.count() >= MAX_NOTES)
			return ResponseEntity.status(429).body("Database has too many notes")

		try {
			val savedNote = noteRepository.save(Note())
			return ResponseEntity.status(201).body(savedNote)
		} catch (e: Exception) {
			return ResponseEntity.status(500).body("Failed to create a note")
		}
	}

	@PatchMapping("/{uuid}")
	fun updateNote(@PathVariable uuid: UUID, @RequestBody body: Map<String, String>): ResponseEntity<Any> {
		val noteOptional = noteRepository.findById(uuid)
		if (!noteOptional.isPresent)
			return ResponseEntity.status(404).body("Did not find the UUID in database")

		val newContent = body["content"]
		if (newContent == null)
			return ResponseEntity.badRequest().body("Missing content field")
		val MAX_LENGTH = 5000
		if (newContent.length >= MAX_LENGTH)
			return ResponseEntity
				.status(HttpStatus.PAYLOAD_TOO_LARGE)
				.body("Content too long. Max allowed is $MAX_LENGTH characters.")

		val note = noteOptional.get()
		note.content = newContent
		try {
			noteRepository.save(note)
			return ResponseEntity.ok().build()
		} catch (e: Exception) {
			return ResponseEntity.status(500).body("Failed to update a note's content")
		}
	}
}
