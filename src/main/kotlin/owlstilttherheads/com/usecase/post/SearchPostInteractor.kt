package owlstilttherheads.com.usecase.post

class SearchPostInteractor(
    private val postRepository: PostRepository,
) : SearchPostUsecase {
    override fun handle(keywords: String): List<PostDto> {
        val posts = postRepository.search(keywords)

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