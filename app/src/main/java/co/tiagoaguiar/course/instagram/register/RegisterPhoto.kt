package co.tiagoaguiar.course.instagram.register

import android.net.Uri
import androidx.annotation.StringRes
import co.tiagoaguiar.course.instagram.common.base.BasePresent
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface RegisterPhoto {

    interface Presenter: BasePresent {
        fun updateUser(photoUri: Uri)
    }
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun onUpdateFailure(message: String)
        fun onUpdateSuccess()
    }
}