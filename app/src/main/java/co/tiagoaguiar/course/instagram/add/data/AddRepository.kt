package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack

class AddRepository(
    private val remoteDataSource: AddFakeRemoteDataSource,
    private val localDataSource: AddLocalDataSource
) {
    fun createPost(uri: Uri, caption: String, callback: RequestCallBack<Boolean> ){
        val userAuth = localDataSource.fetchSession()

        remoteDataSource.createPost(userAuth.uuid, uri, caption, object : RequestCallBack<Boolean>{
            override fun onSuccess(data: Boolean) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
            override fun onComplete() {
                callback.onComplete()
            }
        })
    }
}