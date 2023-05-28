package co.tiagoaguiar.course.instagram.home.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface HomeDataSource {
//  Busca do servidor
    fun fetchFeed(userUUID: String, callback: RequestCallBack<List<Post>>)

    fun fetchSession(): UserAuth { throw UnsupportedOperationException()}

    fun putFeed(response: List<Post>?) { throw UnsupportedOperationException()}

//  Para anexar os posts
    fun putPosts(response: List<Post>) { throw UnsupportedOperationException()}
}