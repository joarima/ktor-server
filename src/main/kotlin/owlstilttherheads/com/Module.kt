package owlstilttherheads.com

import org.koin.dsl.module
import owlstilttherheads.com.infrastructure.post.PostRepositoryImpl
import owlstilttherheads.com.usecase.post.*


val appModules = module {
    single<PostRepository> { PostRepositoryImpl() }
    single { GetPostsInteractor(get()) as GetPostsUsecase }
    single { GetPostWithIdInteractor(get()) as GetPostWithIdUsecase }
    single { UpdatePostInteractor(get()) as UpdatePostUsecase }
}
