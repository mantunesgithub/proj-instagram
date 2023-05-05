package co.tiagoaguiar.course.instagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment: Fragment(R.layout.fragment_image_cropper) {

    private var binding: FragmentImageCropperBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageCropperBinding.bind(view)
/*
        Busca a uri no arguments gravado na activity (openImageCropper)
 */
        val uri = arguments?.getParcelable<Uri>(KEY_URI)
        binding?.let {
            with(it) {
                cropperContainer.setAspectRatio(1,1) //proporcao da imagem quadrada 1:1
                cropperContainer.setFixedAspectRatio(true) // manter a imagem fixa
                cropperContainer.setImageUriAsync(uri)   //infla o layout da imagem do recorte

                // aqui ele tem a imagem para fazer o cropper. Então vamos escutar o botap de Cancel ou Save
                cropperBtnCancel.setOnClickListener {
                  parentFragmentManager.popBackStack()      //força o cancel e tira a view
                }
                /*
                    Para Salvar: - Criar um espaço na memoria para injetar essa arquivo novo recortado
                    -Pegar o diretorio das fotos no sistema, e vai criar uma uri para salvar o arq.
                    -Cria uma Classe File passando o caminho onde vao salvar e o nome do arq
                    - Salva a imagem recortada. Depois precisa escutar onde a nova uri foi salva para
                      anexar na tela do foto do perfil. Entao vamos declarar listen
                       cropperContainer.setOnCropImageCompleteListener. Toda vez que o cropper é feito
                       temos a imagem recortada com caminho da nova img no result.uri
                 */
                cropperBtnSave.setOnClickListener {
                    val dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    if (dir != null) {
                        val uriToSaved = Uri.fromFile(File(dir.path,System.currentTimeMillis().toString() +
                            ".jpeg"))
                        cropperContainer.saveCroppedImageAsync(uriToSaved) //gravar a imagem recortada
                    }
                }
                cropperContainer.setOnCropImageCompleteListener { view, result ->
                    //Log.i("TAG", "onViewCreated:Nova Imagem recortada ${result.uri}")
                    /*  Com a img salva, temos que tirar a tela de salve da frente e mandar uri para tela anterior
                        - CropperImageFragment (recortou a imagem) e passar para RegisterPhotoFragment
                        - Comunicação UM FRAGMENTO PARA OUTRO FRAGMENTO, para isso:
                            - colocamos a bib fragment-ktx no build.app para eles se comunicarem
                        - Quem estiver escutando o resultado deste cropkey vai receber uma nova uri
                        que é o RegisterPhotoFragment no onCreate. Listen entre fragmentos
                        ( setFragmentResult )
                     */
                    setFragmentResult("cropkey", bundleOf(KEY_URI to result.uri))
                    parentFragmentManager.popBackStack()        //tirar a tela de salve da frente
                }

            }
        }
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
    companion object{
        const val KEY_URI = "key_uri"
    }
}