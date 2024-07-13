package owlstilttherheads.com

import org.koin.dsl.module
import owlstilttherheads.com.infrastructure.post.PostRepositoryImpl
import owlstilttherheads.com.usecase.post.GetPostInteractor
import owlstilttherheads.com.usecase.post.GetPostsUsecase
import owlstilttherheads.com.usecase.post.PostRepository


val appModules = module {
    single<PostRepository> { PostRepositoryImpl() }
    single { GetPostInteractor(get()) as GetPostsUsecase }
}
