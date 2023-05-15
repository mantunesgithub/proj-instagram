package co.tiagoaguiar.course.instagram.profile.data
/*
    Cache da tela de perfil
 */
interface ProfileCache<T> {
//  Verifica se tem algo no cache
    fun isCached() : Boolean

//  Retorna um valor gen. a partir da chave
    fun get(key: String) : T?

//  Adicionar um dados do tipo gen.
    fun put(data: T)
}
