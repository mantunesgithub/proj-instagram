package co.tiagoaguiar.course.instagram.splash

import co.tiagoaguiar.course.instagram.common.base.BasePresent
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface Splash {
    interface Presenter: BasePresent {
        fun authenticated()
    }
    interface View: BaseView<Presenter>{
        fun gotoMainScreen()
        fun gotoLoginScreen()
    }
}