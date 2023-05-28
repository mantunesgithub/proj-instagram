package co.tiagoaguiar.course.instagram.search.presenter

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import co.tiagoaguiar.course.instagram.search.Search
import co.tiagoaguiar.course.instagram.search.data.SearchRepository

class SearchPresenter(
    private var view: Search.View?,
    private val repository: SearchRepository
) : Search.Presenter {

    override fun fetchUsers(name: String) {
        view?.showProgress(true)
        repository.fetchUsers(name, object: RequestCallBack<List<UserAuth>> {
            override fun onSuccess(data: List<UserAuth>) {
                if (data.isEmpty()) {
                    view?.displayEmptyUsers()
                } else{
                    view?.displayFullUsers(data)
                }
            }
            override fun onFailure(message: String) {
                view?.displayEmptyUsers()
            }
            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }
    override fun onDestroy() {
        view = null
    }

}