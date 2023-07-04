package co.tiagoaguiar.course.instagram.register.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.extension.hideKeyBoard
import co.tiagoaguiar.course.instagram.common.extension.replaceFragment
import co.tiagoaguiar.course.instagram.common.view.CropperImageFragment
import co.tiagoaguiar.course.instagram.common.view.CropperImageFragment.Companion.KEY_URI
import co.tiagoaguiar.course.instagram.databinding.ActivityRegisterBinding
import co.tiagoaguiar.course.instagram.main.view.MainActivity
import co.tiagoaguiar.course.instagram.register.view.RegisterNamePasswordFragment.Companion.KEY_EMAIL
import co.tiagoaguiar.course.instagram.register.view.RegisterWelcomeFragment.Companion.KEY_NAME
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var currentPhoto: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterEmailFragment()
        replaceFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        //No fim da classe uma outra forma de implementar
        val fragment = RegisterNamePasswordFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_EMAIL, email)
            }
        }
        replaceFragment(fragment)
    }
    /* Vou navegar do frag1 para frag2. O frag1 terminou de fazer o processo, vai comunicar active
        para trocar para frag2. Vai ser via interface. A active vai implementar a interface para
        comunicar entre fragmento e atividade
     */
    override fun goToWelcomeScreen(name: String) {
        val fragment = RegisterWelcomeFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_NAME, name)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToPhotoScreen() {
        val fragment = RegisterPhotoFragment()
        replaceFragment(fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    /*Open Camera. Retorna boolean indicando se a imagem foi salva na uri que fornecemos ou não.
        Se foi salvo ja temos a uri em memoria no currentPhoto: Uri
    * */
    private val getCamera= registerForActivityResult(ActivityResultContracts.
        TakePicture()) { saved ->
        if (saved) {
            openImageCropper(currentPhoto)
        }
    }
    /*
    Vai ser disparado depois que escolher a foto indicando o formato de contrato que ele vai retornar
    que seria o Conteúdo - vai trazer a uri (caminho) da foto na uri: Uri? ->
     */
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            //Log.i("TAG", " Uri: ${uri.toString()}")  - imprime o tipo de uri que retornou. Abriu a galeria
            uri?.let {
            openImageCropper(it)
        }
    }
    /* Vamos fazer a instrução de abrir a galeria - cria uma constatnte getContent. O
       getContent.launch indica o tipo de conteudo que queremos - imagem jpeg ou png.
     */
    override fun goToGalleryScreen() {
        getContent.launch("image/*")
    }

    /* Open app Camera

    */
    override fun goToCameraScreen() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)    //usa para capturar foto
        if (intent.resolveActivity(packageManager) != null) {   //significa que achou um aplicativo de camera
            val photoFile: File? =  try {
                createImageFile()
            } catch (e: IOException) {
                Log.e("RegisterActivity", e.message, e )
                null
            }
            //entao com file pronto podemos pedir para ele gravar e lançar um app de camera injetando
            //as fotos que ele capturar dentro dessa Uri
            // poderia ser:  if photoFile != null
            photoFile?.also {
                //vamos buscar a Uri atraves de um file. A autoridade "nosso pct" precisa dizer para o Android
                // qual é o pct que vai armazanar o arq no Manifest <provider  autoridade "nosso pct"
                val photoUri = FileProvider.getUriForFile(this,
                    "co.tiagoaguiar.course.instagram.fileprovider", it)

                //com a imagem pronta vamos armazenar a foto corrente para enviar para prx tela
                //esse é o caminho da foto temporaria onde ele vai salvar
                currentPhoto = photoUri
                /*-abre o app da camera passando para tirar a foto. Ele chama openImageCropper(uri: Uri?)
                  -Para visualizar arquivo: shift+shift device file explorer/store/self/primary/android/
                  data/pct/files/pictures -> JPEG_2023...
                * */
                getCamera.launch(photoUri)
            }
        }
    }
    // Precisa de um espaço temporario para anexar nosso arq jpeg após o recorte da foto-idem galeria
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // define o nome do arq com a data/hora (google recom). Ele pode lançar uma excessao
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(Date())
        // diretorio onde vai grava o arquivo. Segurança getExternalFilesDir, somente esse app acessa as fotos
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //cria o arq com:                prefixo                    sufixo  diretório onde vai salvar
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", dir)
        // se conseguir gravar retorna o caminho do file em photoFile e
    }


    private fun replaceFragment(fragment: Fragment) {
         replaceFragment(R.id.register_fragment, fragment)

        //* acertos para rodar API 21 *
        hideKeyBoard()      // vai tira o focus do campo de texto
    }
    /*
        Com caminho,vamos fazer o cropper(recorte) passando a uri com arguments
     */
    private fun openImageCropper(uri: Uri?) {
        val fragment = CropperImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_URI, uri)
            }
        }
        replaceFragment(fragment)
    }

}
//  val args = Bundle()
//  args.putString(KEY_EMAIL, email)
//  val fragment = RegisterNamePasswordFragment()
//  fragment.arguments = args
//  val fragment = RegisterNamePasswordFragment()
//  replaceFragment(fragment)
