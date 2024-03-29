package co.tiagoaguiar.course.instagram.login.data

import android.os.Handler
import android.os.Looper
import co.tiagoaguiar.course.instagram.common.model.Database

class FakeDataSource : LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallBack) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

            when {
                userAuth == null -> {
                    callback.onFailure("Usuário não encontrado")
                }
                userAuth.password != password -> {
                    callback.onFailure("Senha está incorreta")
                }
                else -> {
                    Database.sessionAuth = userAuth
                    callback.onSucess()
                }
            }

            callback.onComplete()
        }, 2000)
    }

}
//           if (email == "a@a.com" && password == "12345678") {
//               callback.onSucess()
//           } else {
//                callback.onFailure("Usuario não encontrado")
//           }
