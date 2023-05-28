package co.tiagoaguiar.course.instagram.search.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface SearchDataSource {
     fun fetchUsers (name: String, callBack: RequestCallBack<List<UserAuth>>)
}