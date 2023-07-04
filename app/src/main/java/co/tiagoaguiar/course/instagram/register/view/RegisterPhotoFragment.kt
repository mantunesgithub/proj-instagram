 package co.tiagoaguiar.course.instagram.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.view.CropperImageFragment
import co.tiagoaguiar.course.instagram.common.view.CustomDialog
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterPhotoBinding
import co.tiagoaguiar.course.instagram.register.RegisterPhoto
import co.tiagoaguiar.course.instagram.register.presentation.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo), RegisterPhoto.View  {

    private var binding: FragmentRegisterPhotoBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null
    override lateinit var presenter: RegisterPhoto.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* -Listen entre fragmentos (CropperImageFragment onde gravou uri bungle) e temos a uri
            com img recortada para anexar na tela
        */
        setFragmentResultListener("cropkey") { requestKey, bundle ->  
            val uri = bundle.getParcelable<Uri>(CropperImageFragment.KEY_URI)
            onCropImageResult(uri)                      //img recortada para anexar na tela
        }
    }

    private fun onCropImageResult(uri: Uri?) {
        if (uri != null) {
            val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)       //retorna o bitmap api >= 28
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)  //retorna o bitmap api < 28
            }
            binding?.registerImgProfile?.setImageBitmap(bitmap)
            /*
                Chama o presenter para armazenar no servidor a imagem recortada
             */
            presenter.updateUser(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = DependencyInjector.registerEmailRepository()
        binding = FragmentRegisterPhotoBinding.bind(view)
        presenter = RegisterPhotoPresenter(this, repository)

        binding?.let {
            with(it) {

                when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)){
                    Configuration.UI_MODE_NIGHT_YES -> {
                        registerImgProfile.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                    }
                }

                registerBtnJump.setOnClickListener {
                    fragmentAttachListener?.goToMainScreen()
                }
                registerBtnNext.isEnabled = true
                registerBtnNext.setOnClickListener {
                     openDialog()
                }
            }
        }
    }
    /* Abrir Galeria: precisa activity pq ela adm telas e recursos do sistema. usa evento do  fragmentAttachListener
    */
    private fun openDialog() {
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo, R.string.gallery) {
            when (it.id) {
                R.string.photo -> {
                    fragmentAttachListener?.goToCameraScreen()
                }
                R.string.gallery -> {
                    fragmentAttachListener?.goToGalleryScreen()
                }
            }
        }
        customDialog.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener ) {
            fragmentAttachListener = context
        }
    }


    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess() {
        fragmentAttachListener?.goToMainScreen()
    }
}