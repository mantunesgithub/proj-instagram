package co.tiagoaguiar.course.instagram.search.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.UserAuth
class SearchRepository(private val dataSource: SearchDataSource) {

    fun fetchUsers(name: String, callback: RequestCallBack<List<UserAuth>>) {
        dataSource.fetchUsers(name, object : RequestCallBack<List<UserAuth>> {
            override fun onSuccess(data: List<UserAuth>) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure((message))
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }
}