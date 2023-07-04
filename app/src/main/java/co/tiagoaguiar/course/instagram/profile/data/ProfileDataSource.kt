package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface ProfileDataSource {
//  Busca do servidor
    fun fetchUserProfile(userUUID: String, callback: RequestCallBack<Pair<User, Boolean?>>)

    fun fetchUserPosts(userUUID: String, callback: RequestCallBack<List<Post>>)

    fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallBack<Boolean>)
        { throw UnsupportedOperationException()}

/*  Busca de algum lugar - Se o acesso remoto chamar esses metodo vai dar crach pq esses metodos
    são de acesso a cache
* */
    fun fetchSession(): String { throw UnsupportedOperationException()}

//  Gravar no cache o usuário buscado
    fun putUser(response: Pair<User, Boolean?>?) { throw UnsupportedOperationException()}

//  Para anexar os posts
    fun putPosts(response: List<Post>?) { throw UnsupportedOperationException()}
}