package owlstilttherheads.com.usecase.post

import org.jdbi.v3.core.Handle
import owlstilttherheads.com.domain.post.Post
import java.util.*

interface PostRepository {
    fun findAll(): List<Post>
    fun findById(id: UUID): Post?
    fun search(keywords: String): List<Post>
    fun update(handle: Handle, post: Post)
    fun create(handle: Handle, post: Post)
    fun delete(handle: Handle, id: UUID)
}