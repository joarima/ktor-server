package owlstilttherheads.com.usecase.post

import java.util.*

interface DeletePostUsecase {
    fun handle(id: UUID)
}