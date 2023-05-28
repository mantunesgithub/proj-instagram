package co.tiagoaguiar.course.instagram.home.data
import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.Post

class HomeFakeRemoteDataSource: HomeDataSource {
    override fun fetchFeed(userUUID: String, callback: RequestCallBack<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val feed = Database.feeds[userUUID]
            callback.onSuccess(feed?.toList() ?: emptyList())
            callback.onComplete()
        }, 2000)
    }
}