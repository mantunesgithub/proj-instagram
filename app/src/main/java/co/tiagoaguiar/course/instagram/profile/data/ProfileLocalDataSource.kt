package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth

class ProfileLocalDataSource(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>
) : ProfileDataSource {
    //  Busca do cache
    override fun fetchUserProfile(userUUID: String, callback: RequestCallBack<Pair<User, Boolean?>>) {
        val userAuth = profileCache.get(userUUID)
        if (userAuth != null) {
            callback.onSuccess(userAuth)
        } else {
            callback.onFailure("Usuario não encontrado")
        }
        callback.onComplete()
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallBack<List<Post>>) {
        val posts = postsCache.get(userUUID)
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

    //  Joga dentro do cache o usuario a primeira vez
    override fun putUser(response: Pair<User, Boolean?>?) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>?) {
        postsCache.put(response)
    }

}
