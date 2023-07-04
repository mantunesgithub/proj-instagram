package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import javax.sql.DataSource

/*
    Criação do Cache
    1. Model, o repository vai decidir se pega do servidor remoto ou do cache.
    2. Vamos criar um factory ProfileDataSourceFactory, ele vai criar um data source local (cache)
       ou um data source remote (servidor).

*/
class ProfileRepository(private val dataSourceFactory: ProfileDataSourceFactory) {


    fun clearCache() {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.putPosts(null)
        localDataSource.putUser(null)
    }

    fun fetchUserProfile(uuid: String?, callback: RequestCallBack<Pair<User, Boolean?>>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userId = uuid ?: localDataSource.fetchSession()

        //  local data source ou remoto
        val dataSource = dataSourceFactory.createFromUser(uuid)
        dataSource.fetchUserProfile(userId, object : RequestCallBack<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
                if (uuid == null) {
                    localDataSource.putUser(data)
                }
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure((message))
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

    fun fetchUserPosts(uuid: String?, callback: RequestCallBack<List<Post>>) {
        val localDataSource =  dataSourceFactory.createLocalDataSource()
        val userId= uuid ?: localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromPosts(uuid)

        dataSource.fetchUserPosts(userId, object : RequestCallBack<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (uuid == null) {
                    localDataSource.putPosts(data)
                }
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure((message))
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }
    fun followUser(uuid: String?, follow: Boolean, callback: RequestCallBack<Boolean>){
        val localDataSource =  dataSourceFactory.createLocalDataSource()
        val userId= uuid ?: localDataSource.fetchSession()
        val dataSource =  dataSourceFactory.createRemoteDataSource()

        dataSource.followUser(userId, follow, object : RequestCallBack<Boolean> {
            override fun onSuccess(data: Boolean) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure((message))
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }
}