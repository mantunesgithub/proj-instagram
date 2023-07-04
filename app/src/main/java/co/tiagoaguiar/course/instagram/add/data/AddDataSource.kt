package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface AddDataSource {

    fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallBack<Boolean>){
        throw java.lang.UnsupportedOperationException()}

    fun fetchSession() : String { throw java.lang.UnsupportedOperationException()}
}