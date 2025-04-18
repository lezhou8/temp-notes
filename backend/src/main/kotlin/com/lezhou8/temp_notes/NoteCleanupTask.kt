package com.lezhou8.temp_notes

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class NoteCleanupTask(private val noteRepository: NoteRepository) {

	@Scheduled(cron = "0 0 0/6 * * ?")
	fun deleteExpiredNotes() {
		val expiredNotes = noteRepository.findByExpiryBefore(Instant.now())
		if (expiredNotes.isNotEmpty()) noteRepository.deleteAll(expiredNotes)
	}
}
