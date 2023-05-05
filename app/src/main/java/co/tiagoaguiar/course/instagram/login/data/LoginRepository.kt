package co.tiagoaguiar.course.instagram.login.data

class LoginRepository (private val dataSource: LoginDataSource)  {
    /*
      Vai ser responsável em decidir o que fazer com esses dados, vai chamar servidor ou um banco
      local
    */
    fun login(
        email: String,
        password: String,
        callback: LoginCallBack
    ) {
        dataSource.login(email,password,callback)
    }
}