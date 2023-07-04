package co.tiagoaguiar.course.instagram.profile

import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User

interface Profile {
    interface Presenter: BasePresenter {
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPost(uuid: String?)
        fun followUser(uuid: String?, follow: Boolean)
        fun clear()
    }
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(user: Pair<User, Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPost(posts: List<Post>)
        fun followUpdated()
    }
}