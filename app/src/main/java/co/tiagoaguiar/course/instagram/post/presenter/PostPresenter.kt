package co.tiagoaguiar.course.instagram.post.presenter
import android.net.Uri
import co.tiagoaguiar.course.instagram.common.model.Database.posts
import co.tiagoaguiar.course.instagram.post.Post
import co.tiagoaguiar.course.instagram.post.data.PostRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var view: Post.View?,
    private val repository: PostRepository
) : Post.Presenter, CoroutineScope {

    private val job = Job()
    override val coroutineContext:  CoroutineContext = job + Dispatchers.IO
    private var uri: Uri? = null

    override fun fetchPictures() {
                        //chamada na thread Main (UI)
        view?.showProgress(true)

        launch {
                        //dentro do launch acontece a chamada paralelea coroutina IO
            val pictures = repository.fetchPictures()

            withContext(Dispatchers.Main) {
                        //executa na main Thread
                if (pictures.isEmpty()) {
                     view?.displayEmptyPictures()
                } else {
                    view?.displayFullPictures(pictures)
                }
                view?.showProgress(false)
            }
        }
    }

    override fun selectUri(uri: Uri) {
        this.uri = uri
    }

    override fun getSelectedUri(): Uri? {
        return uri
    }

    override fun onDestroy() {
        view = null
        job.cancel()
    }
}