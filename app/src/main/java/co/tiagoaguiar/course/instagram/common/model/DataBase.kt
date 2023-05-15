package co.tiagoaguiar.course.instagram.common.model

import java.util.*

object Database {

    val usersAuth = hashSetOf<UserAuth>()
    val photos = hashSetOf<Photo>()
    val posts = hashMapOf<String, Set<Post>>()
    /*
        "userA" : ["post1", "post2"]
        "userB" : ["post1", "post2", "post3"]
     */
    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "UserA", "userA@gmail.com", "12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "UserB", "userB@gmail.com", "87654321"))
        sessionAuth = usersAuth.first()
    }
}