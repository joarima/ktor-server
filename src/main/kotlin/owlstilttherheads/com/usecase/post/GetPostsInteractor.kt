package owlstilttherheads.com.usecase.post

class GetPostsInteractor(
    private val postRepository: PostRepository
) : GetPostsUsecase {
    override fun handle(): List<PostDto> {
        val posts = postRepository.findAll()

        return posts.map {
            PostDto(
                id = it.id,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                content = it.content,
                isOpen = it.isOpen,
                order = it.order,
            )
        }
    }
}