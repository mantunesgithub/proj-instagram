package co.tiagoaguiar.course.instagram.add.data

import android.net.Uri
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import java.util.*

class AddFakeRemoteDataSource: AddDataSource {
    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallBack<Boolean>
    ) {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            var posts = Database.posts[userUUID]
            if (posts == null) {
                posts = mutableSetOf()
                Database.posts[userUUID] = posts
            }
            //TODO: REMOVER ESSA CLASSE
            val post = Post(UUID.randomUUID().toString(), null, caption, System.currentTimeMillis(),
            null)
            posts.add(post)

            var followers = Database.followers[userUUID]
            if (followers == null) {
                followers = mutableSetOf()
                Database.followers[userUUID] = followers
            } else {
                for (follower in followers) {
                    Database.feeds[follower]?.add(post)
                }
                Database.feeds[userUUID]?.add(post)
            }
            callback.onSuccess(true)
        }, 1000)
    }
}