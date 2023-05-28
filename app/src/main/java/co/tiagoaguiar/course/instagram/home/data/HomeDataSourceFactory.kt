package co.tiagoaguiar.course.instagram.home.data
import co.tiagoaguiar.course.instagram.common.base.Cache
import co.tiagoaguiar.course.instagram.common.model.Post

/*
    Fabrica de datasource - Recebe um parametro onde vai armaz o cache do perfil e do post
    Interface HomeCache generica onde vai informar o tipo de cache que precisa fazer
 */
class HomeDataSourceFactory(
    private val feedCache: Cache<List<Post>>
) {
    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }
    fun createFromFeed():   HomeDataSource {
        if (feedCache.isCached()) {
            return HomeLocalDataSource(feedCache)
        }
        return HomeFakeRemoteDataSource()
    }
}
