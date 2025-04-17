package com.lezhou8.temp_notes

import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Entity
@Table(name = "notes")
data class Note (
	@Id
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	val uuid: UUID = UUID.randomUUID(),

	@Column(nullable = false)
	var content: String = "",

	@Column(updatable = false, nullable = false)
	val expiry: Instant = Instant.now().plus(7, ChronoUnit.DAYS)
)
