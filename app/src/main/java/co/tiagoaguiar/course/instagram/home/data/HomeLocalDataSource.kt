package co.tiagoaguiar.course.instagram.home.data

import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth

class HomeLocalDataSource(
    private val feedCache: Cache<List<Post>>
) : HomeDataSource {
    //  Busca do cache

    override fun fetchFeed(userUUID: String, callback: RequestCallBack<List<Post>>) {
        val posts = feedCache.get(userUUID)
        if (posts != null) {
            callback.onSuccess(posts)
        } else {
            callback.onFailure("posts não existem")
        }
        callback.onComplete()
    }

    //  Retorna do bd o user da session
    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuario não logado !!")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }
}
