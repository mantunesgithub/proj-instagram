package co.tiagoaguiar.course.instagram.profile

import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface Profile {
    interface Presenter: BasePresenter {
        fun fetchProfile()
        fun fetchUserPost()
    }
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(UserAuth: UserAuth)
        fun displayRequestFailure(message: String)
        fun displayEmptyPost()
        fun displayFullPost(posts: List<Post>)

    }

}