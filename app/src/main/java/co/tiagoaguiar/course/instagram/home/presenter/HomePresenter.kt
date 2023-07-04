package co.tiagoaguiar.course.instagram.home.presenter

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.home.Home
import co.tiagoaguiar.course.instagram.home.data.HomeRepository

class HomePresenter(
    private var view: Home.View?,
    private val repository: HomeRepository
) : Home.Presenter {

    override fun fetchFeed() {
        view?.showProgress(true)
        repository.fetchFeed(object: RequestCallBack<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPost()
                } else{
                    view?.displayFullPost(data)
                }
            }
            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }
            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun clear() {
        repository.clearCashe()
    }
    override fun logout() {
        repository.logout()
    }

    override fun onDestroy() {
        view = null
    }
}