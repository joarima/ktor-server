package owlstilttherheads.com.usecase.post

interface GetPostsUsecase {
    fun handle(): List<PostDto>
}

