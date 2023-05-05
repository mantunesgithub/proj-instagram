package co.tiagoaguiar.course.instagram.register

import androidx.annotation.StringRes
import co.tiagoaguiar.course.instagram.common.base.BasePresent
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface RegisterNameAndPassword {
    interface Presenter: BasePresent {
        fun create(email: String, name: String, password: String, confirm: String)
    }
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)

        fun displayNameFailure(@StringRes nameError: Int?)

        fun displayPasswordFailure(@StringRes passError: Int?)

        fun onCreateFailure(message: String)

        fun onCreateSuccess(name: String)
    }
}