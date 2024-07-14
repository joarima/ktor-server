package owlstilttherheads.com.usecase.post

import owlstilttherheads.com.domain.post.Post
import java.util.*

interface PostRepository {
    fun findAll(): List<Post>
    fun findById(id: UUID): Post?
}