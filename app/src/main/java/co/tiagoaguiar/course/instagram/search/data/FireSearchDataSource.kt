package co.tiagoaguiar.course.instagram.search.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallBack
import co.tiagoaguiar.course.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireSearchDataSource : SearchDataSource {
    override fun fetchUsers(name: String, callBack: RequestCallBack<List<User>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereGreaterThanOrEqualTo("name", name)
            .whereLessThanOrEqualTo("name", name + "\uf8ff")
            .get()
            .addOnSuccessListener { res ->
                val documents = res.documents
                val users = mutableListOf<User>()
                for (document in documents) {
                    val user = document.toObject(User::class.java)
                    if (user != null && user.uuid != FirebaseAuth.getInstance().uid) {
                        users.add(user)
                    }
                }
                callBack.onSuccess(users)
            }
            .addOnFailureListener { exception ->
                callBack.onFailure(exception.message ?: "Falha ao buscar usuário")
            }
            .addOnCompleteListener {
                callBack.onComplete()
            }
    }
}