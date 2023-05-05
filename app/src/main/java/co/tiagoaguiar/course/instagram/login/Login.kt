package co.tiagoaguiar.course.instagram.login

import androidx.annotation.StringRes
import co.tiagoaguiar.course.instagram.common.base.BasePresent
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface Login {

    interface Presenter: BasePresent {
        fun login(email: String, password: String)

    }
    //Camada View
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message: String)
    }
}