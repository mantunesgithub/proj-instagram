package co.tiagoaguiar.course.instagram.add.data

import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class AddLocalDataSource: AddDataSource {
    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuario não logado!")
    }
}