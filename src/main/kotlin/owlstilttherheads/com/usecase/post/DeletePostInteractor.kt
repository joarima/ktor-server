package owlstilttherheads.com.usecase.post

import org.jdbi.v3.core.Jdbi
import owlstilttherheads.com.infrastructure.Database
import java.util.*

class DeletePostInteractor(
    private val postRepository: PostRepository,
    private val jdbi: Jdbi = Database.jdbi
) : DeletePostUsecase {
    override fun handle(id: UUID) {
        jdbi.useHandle<Exception> { handle ->
            try {
                postRepository.delete(handle, id)
            } catch (e: Exception) {
                handle.rollback()
                throw Exception("error deleting post with id: $id", e)
            }
        }
    }
}