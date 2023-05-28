package co.tiagoaguiar.course.instagram.search.data

import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.Data
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import co.tiagoaguiar.course.instagram.profile.data.ProfileDataSource

class SearchFakeRemoteDataSource: SearchDataSource {
    override fun fetchUsers(name: String, callBack: RequestCallBack<List<UserAuth>>) {
         Handler(Looper.getMainLooper()).postDelayed({
            val users = Database.usersAuth.filter {
                it.name.lowercase().startsWith(name.lowercase()) &&
                it.uuid != Database.sessionAuth!!.uuid
            }
            callBack.onSuccess(users.toList())

             callBack.onComplete()
        }, 2000)
    }
}