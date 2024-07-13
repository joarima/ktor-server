package owlstilttherheads.com.usecase.post

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import owlstilttherheads.com.utils.serializer.OffsetDateTimeSerializer
import owlstilttherheads.com.utils.serializer.UuidSerializer
import java.time.OffsetDateTime
import java.util.*

interface GetPostsUsecase {
    fun handle(): List<Post>
}

@Serializable
data class Post(
    @Serializable(with = UuidSerializer::class) val id: UUID,
    @Serializable(with = OffsetDateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class) val updatedAt: OffsetDateTime,
    val content: JsonElement,
    val isOpen: Boolean,
    val order: Int,
)


