package owlstilttherheads.com.domain.post

import kotlinx.serialization.json.JsonElement
import java.time.OffsetDateTime
import java.util.*

data class Post(
    val id: UUID,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val content: JsonElement,
    val isOpen: Boolean,
    val order: Int,
)
