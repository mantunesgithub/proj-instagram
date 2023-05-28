package co.tiagoaguiar.course.instagram.add.data

import co.tiagoaguiar.course.instagram.common.model.Database
import co.tiagoaguiar.course.instagram.common.model.UserAuth

class AddLocalDataSource: AddDataSource {
    override fun fetchSession(): UserAuth {
        return Database.sessionAuth?: throw RuntimeException("Usuario não logado!")
    }
}