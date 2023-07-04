package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack

class AddRepository(
    private val remoteDataSource: FireAddDataSource,
    private val localDataSource: AddLocalDataSource
) {
    fun createPost(uri: Uri, caption: String, callback: RequestCallBack<Boolean>){
        val uuid = localDataSource.fetchSession()

        remoteDataSource.createPost(uuid, uri, caption, object : RequestCallBack<Boolean> {
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