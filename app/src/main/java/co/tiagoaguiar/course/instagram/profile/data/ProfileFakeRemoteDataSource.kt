package co.tiagoaguiar.course.instagram.profile.data

import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.Data
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
// Pair<UserAuth, Boolean?>
// Null = Perfil atual/corrente  True=Estou seguindo  False=Para seguir
class ProfileFakeRemoteDataSource: ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallBack<Pair<User, Boolean?>>){
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid}
            if (userAuth != null) {
                if (userAuth == Database.sessionAuth) {
                    // TODO: remover esta classe callback.onSuccess(Pair(userAuth, null))
                }else{
                    val followings = Database.followers[Database.sessionAuth!!.uuid]
                    val destUser = followings?.firstOrNull { it == userUUID}
                    // TODO: remover esta classe callback.onSuccess(Pair(userAuth,  destUser != null))
                }
            } else {
                callback.onFailure("Usuário não encontrado")
            }
            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallBack<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val posts = Database.posts[userUUID]
            callback.onSuccess(posts?.toList() ?: emptyList())
            callback.onComplete()
        }, 2000)
    }
    override fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallBack<Boolean>){
        Handler(Looper.getMainLooper()).postDelayed({
            var followers = Database.followers[Database.sessionAuth!!.uuid]
            if (followers == null) {
                followers = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid]  = followers
            }
            if (isFollow) {
                Database.followers[Database.sessionAuth!!.uuid]!!.add(userUUID)
            } else{
                Database.followers[Database.sessionAuth!!.uuid]!!.remove(userUUID)
            }
            callback.onSuccess(true)
            callback.onComplete()
        }, 500)
    }

}