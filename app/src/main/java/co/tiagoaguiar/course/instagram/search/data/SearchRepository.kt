package co.tiagoaguiar.course.instagram.search.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.User

class SearchRepository(private val dataSource: SearchDataSource) {

    fun fetchUsers(name: String, callback: RequestCallBack<List<User>>) {
        dataSource.fetchUsers(name, object : RequestCallBack<List<User>> {
            override fun onSuccess(data: List<User>) {
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