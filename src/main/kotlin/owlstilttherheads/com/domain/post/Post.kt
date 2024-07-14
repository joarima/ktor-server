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
) {
    companion object {
        fun create(
            id: UUID = UUID.randomUUID(),
            content: JsonElement,
            isOpen: Boolean = false,
        ): Post {
            return Post(
                id = id,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                content = content,
                isOpen = isOpen,
                order = 0,
            )
        }

        fun restore(
            id: UUID,
            createdAt: OffsetDateTime,
            updatedAt: OffsetDateTime,
            content: JsonElement,
            isOpen: Boolean,
            order: Int,
        ): Post {
            return Post(
                id = id,
                createdAt = createdAt,
                updatedAt = updatedAt,
                content = content,
                isOpen = isOpen,
                order = order
            )
        }
    }
}
