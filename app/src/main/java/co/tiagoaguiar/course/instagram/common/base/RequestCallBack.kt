package co.tiagoaguiar.course.instagram.common.base

interface RequestCallBack<T>  {
    fun onSuccess(data: T)
    fun onFailure(message: String)
    fun onComplete()
}