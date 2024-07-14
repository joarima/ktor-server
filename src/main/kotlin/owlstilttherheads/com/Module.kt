package owlstilttherheads.com

import org.koin.dsl.module
import owlstilttherheads.com.infrastructure.post.PostRepositoryImpl
import owlstilttherheads.com.usecase.post.*


val appModules = module {
    single<PostRepository> { PostRepositoryImpl() }
    single { GetPostsInteractor(get()) as GetPostsUsecase }
    single { GetPostWithIdInteractor(get()) as GetPostWithIdUsecase }
    single { SearchPostInteractor(get()) as SearchPostUsecase }
    single { UpdatePostInteractor(get()) as UpdatePostUsecase }
    single { CreatePostInteractor(get()) as CreatePostUsecase }
    single { DeletePostInteractor(get()) as DeletePostUsecase }
}
