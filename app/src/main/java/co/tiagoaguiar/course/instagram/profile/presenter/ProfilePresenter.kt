package co.tiagoaguiar.course.instagram.profile.presenter

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.User
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import co.tiagoaguiar.course.instagram.profile.Profile
import co.tiagoaguiar.course.instagram.profile.data.ProfileRepository
import java.util.UUID

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
) : Profile.Presenter {

    override fun clear() {
        repository.clearCache()
    }

    override fun fetchUserProfile(uuid: String?) {
        view?.showProgress(true)
        //val userUUID = Database.sessionAuth?.uuid ?: throw RuntimeException("user not found")
        repository.fetchUserProfile(uuid, object : RequestCallBack<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
                view?.displayUserProfile(data)
            }
            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }
            override fun onComplete() {
            }
        })
    }
    override fun fetchUserPost(uuid: String?) {
       // val userUUID = Database.sessionAuth?.uuid ?: throw RuntimeException("user not found")
        repository.fetchUserPosts(uuid, object: RequestCallBack<List<Post>> {
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

    override fun followUser(uuid: String?, follow: Boolean) {
        repository.followUser(uuid, follow, object : RequestCallBack<Boolean> {
            override fun onSuccess(data: Boolean) {
                fetchUserProfile(uuid)
                if (data) {
                    view?.followUpdated()
                }
            }
            override fun onFailure(message: String) {}
            override fun onComplete() {}
        })
    }

    override fun onDestroy() {
        view = null
    }

}