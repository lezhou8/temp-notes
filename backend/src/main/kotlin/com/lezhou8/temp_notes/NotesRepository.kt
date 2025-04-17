package com.lezhou8.temp_notes

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NoteRepository : JpaRepository<Note, UUID>
