package owlstilttherheads.com.usecase.post

import owlstilttherheads.com.domain.post.Post

interface PostRepository {
    fun findAll(): List<Post>
}