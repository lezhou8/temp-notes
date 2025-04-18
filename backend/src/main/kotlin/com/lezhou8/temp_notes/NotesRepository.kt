package com.lezhou8.temp_notes

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import java.time.Instant

interface NoteRepository : JpaRepository<Note, UUID> {
	fun findByExpiryBefore(now: Instant): List<Note>
}
