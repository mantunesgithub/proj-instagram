package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth
/*
    Criação do Cache
    1. Model, o repository vai decidir se pega do servidor remoto ou do cache.
    2. Vamos criar um factory ProfileDataSourceFactory, ele vai criar um data source local (cache)
       ou um data source remote (servidor).

*/
class ProfileRepository(private val dataSourceFactory: ProfileDataSourceFactory) {
    fun fetchUserProfile(callback: RequestCallBack<UserAuth>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val usertAuth = localDataSource.fetchSession()

        //  local data source ou remoto
        val dataSource = dataSourceFactory.createFromUser()
        dataSource.fetchUserProfile(usertAuth.uuid, object : RequestCallBack<UserAuth> {
            override fun onSuccess(data: UserAuth) {
                localDataSource.putUser(data)
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

    fun fetchUserPosts(callback: RequestCallBack<List<Post>>) {
        val localDataSource =  dataSourceFactory.createLocalDataSource()
        val userAuth = localDataSource.fetchSession()
        val dataSource = dataSourceFactory.createFromPosts()
        dataSource.fetchUserPosts(userAuth.uuid, object : RequestCallBack<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                localDataSource.putPosts(data)
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