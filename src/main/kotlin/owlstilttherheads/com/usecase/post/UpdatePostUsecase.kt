package owlstilttherheads.com.usecase.post

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import owlstilttherheads.com.utils.serializer.UuidSerializer
import java.util.*

interface UpdatePostUsecase {
    fun handle(updatePost: UpdatePostDto)
}

@Serializable
data class UpdatePostDto(
    @Serializable(with = UuidSerializer::class) val id: UUID,
    val content: JsonElement,
    val isOpen: Boolean
)