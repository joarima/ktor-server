package owlstilttherheads.com.usecase.post

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

interface CreatePostUsecase {
    fun handle(createPost: CreatePostDto)
}

@Serializable
data class CreatePostDto(
    val content: JsonElement,
    val isOpen: Boolean
)