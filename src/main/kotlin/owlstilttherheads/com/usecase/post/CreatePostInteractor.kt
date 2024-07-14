package owlstilttherheads.com.usecase.post

import org.jdbi.v3.core.Jdbi
import owlstilttherheads.com.domain.post.Post
import owlstilttherheads.com.infrastructure.Database

class CreatePostInteractor(
    private val postRepository: PostRepository,
    private val jdbi: Jdbi = Database.jdbi
) : CreatePostUsecase {
    override fun handle(createPost: CreatePostDto) {
        val createdPost = Post.create(
            content = createPost.content,
            isOpen = createPost.isOpen
        )

        jdbi.useHandle<Exception> { handle ->
            try {
                postRepository.create(handle, createdPost)
            } catch (e: Exception) {
                handle.rollback()
                throw Exception("error creating post $createdPost", e)
            }
        }
    }
}