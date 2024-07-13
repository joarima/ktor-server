package owlstilttherheads.com.usecase.post

class GetPostInteractor(
    private val postRepository: PostRepository
) : GetPostsUsecase {
    override fun handle(): List<Post> {
        val posts = postRepository.findAll()

        return posts.map {
            Post(
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