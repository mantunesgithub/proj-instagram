package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

/*
    Fabrica de datasource - Recebe um parametro onde vai armaz o cache do perfil e do post
    Interface ProfileCache generica onde vai informar o tipo de cache que precisa fazer
 */
class ProfileDataSourceFactory(
    private val profileCache: ProfileCache <UserAuth>,
    private val postsCache: ProfileCache<List<Post>>
) {

    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }
    //  Metodo para verificar no repository da onde ele vai buscar informações
    fun createFromUser() : ProfileDataSource {
        if (profileCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }
        return FakeProfileRemoteDataSource()
    }
    fun createFromPosts():   ProfileDataSource {
        if (postsCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }
        return FakeProfileRemoteDataSource()
    }
}
