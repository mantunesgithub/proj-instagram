package co.tiagoaguiar.course.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.*

object Database {

    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()
    /*
        "userA" : ["post1", "post2"]
        "userB" : ["post1", "post2", "post3"]
     */
    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(UUID.randomUUID().toString(),
            "UserA",  "userA@gmail.com", "12345678", Uri.fromFile(
                File("/storage/emulated/0/Android/media/co.tiagoaguiar.course.instagram/Instagram/2023-05-23-01-12-39-821.jpg")))
        val userB = UserAuth(UUID.randomUUID().toString(),
            "UserB", "userB@gmail.com", "87654321", Uri.fromFile(
                File("/storage/emulated/0/Android/media/co.tiagoaguiar.course.instagram/Instagram/2023-05-23-01-12-39-821.jpg")))
        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()
/*
        feeds[userA.uuid]?.addAll(
            arrayListOf(
                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/emulated/0/Android/media/co.tiagoaguiar.course.instagram/Instagram/2023-05-23-01-12-39-821.jpg")),
                "desc", System.currentTimeMillis(), userA),
                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/emulated/0/Android/media/co.tiagoaguiar.course.instagram/Instagram/2023-05-23-01-12-39-821.jpg")),
                "desc", System.currentTimeMillis(), userA),
                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/emulated/0/Android/media/co.tiagoaguiar.course.instagram/Instagram/2023-05-23-01-12-39-821.jpg")),
                "desc", System.currentTimeMillis(), userA),
                Post(UUID.randomUUID().toString(), Uri.fromFile(
                    File("/storage/emulated/0/Android/media/co.tiagoaguiar.course.instagram/Instagram/2023-05-23-01-12-39-821.jpg")),
                "desc", System.currentTimeMillis(), userA)
            )
        )
        feeds[userA.uuid]?.toList()?.let {
            feeds[userB.uuid]?.addAll(it)
        }
 */
        for (i in 0..30) {
            val user = UserAuth(UUID.randomUUID().toString(),"User$i","user$i@gmail.com",
                "123123123", null)
            usersAuth.add(user)
        }
        sessionAuth = usersAuth.first()
        followers[sessionAuth!!.uuid]?.add(usersAuth[2].uuid)
    }
}