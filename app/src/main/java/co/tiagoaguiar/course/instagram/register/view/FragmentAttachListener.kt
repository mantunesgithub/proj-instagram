package co.tiagoaguiar.course.instagram.register.view
/*
     Toda vez que um fragmento for anexada em uma atividade, essa atividade vai escutar
     qualquer mudança dessa atividade
 */
interface FragmentAttachListener {
     fun goToNameAndPasswordScreen(email: String)
     fun goToWelcomeScreen(name: String)
     fun goToPhotoScreen()
     fun goToMainScreen()
     fun goToGalleryScreen()
     fun goToCameraScreen()
}