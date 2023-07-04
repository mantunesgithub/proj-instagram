package co.tiagoaguiar.course.instagram.home

import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface Home {
    interface Presenter: BasePresenter {
        fun fetchFeed()
        fun logout()
        fun clear()
    }
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPost(posts: List<Post>)

    }

}