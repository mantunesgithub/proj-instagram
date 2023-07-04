package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth

/*
    Fabrica de datasource - Recebe um parametro onde vai armaz o cache do perfil e do post
    Interface ProfileCache generica onde vai informar o tipo de cache que precisa fazer
 */
class ProfileDataSourceFactory(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }
    fun createRemoteDataSource(): ProfileDataSource {
        return FireProfileDataSource()
    }
    //  Metodo para verificar no repository da onde ele vai buscar informações
    fun createFromUser(uuid: String?) : ProfileDataSource {
        if (uuid != null)
            return createRemoteDataSource()

        if (profileCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }
        return createRemoteDataSource()
    }
    fun createFromPosts(uuid: String?):   ProfileDataSource {
        if (uuid != null)
            return createRemoteDataSource()

        if (postsCache.isCached()) {
            return ProfileLocalDataSource(profileCache, postsCache)
        }
        return createRemoteDataSource()
    }
}
