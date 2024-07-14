package owlstilttherheads.com.usecase.post

interface SearchPostUsecase {
    fun handle(keywords: String): List<PostDto>
}