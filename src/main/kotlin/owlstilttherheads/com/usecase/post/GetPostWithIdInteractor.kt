package owlstilttherheads.com.usecase.post

import java.util.*

class GetPostWithIdInteractor(
    private val postRepository: PostRepository
) : GetPostWithIdUsecase {
    override fun handle(id: UUID): PostDto? {
        return postRepository.findById(id)?.let {
            PostDto(
                id = it.id,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                content = it.content,
                isOpen = it.isOpen,
                order = it.order
            )
        }
    }
}