package owlstilttherheads.com.usecase.post

import org.jdbi.v3.core.Jdbi
import owlstilttherheads.com.domain.post.Post
import owlstilttherheads.com.infrastructure.Database

class UpdatePostInteractor(
    private val postRepository: PostRepository,
    private val jdbi: Jdbi = Database.jdbi
) : UpdatePostUsecase {
    override fun handle(updatePost: UpdatePostDto) {
        val updatedPost = Post.create(
            id = updatePost.id,
            content = updatePost.content,
            isOpen = updatePost.isOpen
        )

        jdbi.useHandle<Exception> { handle ->
            try {
                postRepository.update(handle, updatedPost)
                println(updatedPost)
            } catch (e: Exception) {
                handle.rollback()
                throw Exception("error updating post $updatedPost", e)
            }
        }
    }
}