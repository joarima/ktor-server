package owlstilttherheads.com.usecase.post

import java.util.*

interface GetPostWithIdUsecase {
    fun handle(id: UUID): PostDto?
}
