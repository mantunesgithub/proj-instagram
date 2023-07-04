package co.tiagoaguiar.course.instagram.login.data

import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface LoginCallBack {
    fun onSucess()
    fun onFailure(message: String)
    fun onComplete()
}
