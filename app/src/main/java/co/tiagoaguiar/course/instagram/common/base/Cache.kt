package co.tiagoaguiar.course.instagram.common.base
/*
    Cache da tela de perfil
 */
interface Cache<T> {
//  Verifica se tem algo no cache
    fun isCached() : Boolean

//  Retorna um valor gen. a partir da chave
    fun get(key: String) : T?

//  Adicionar um dados do tipo gen.
    fun put(data: T?)
}
