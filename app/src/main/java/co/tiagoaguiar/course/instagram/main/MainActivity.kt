package co.tiagoaguiar.course.instagram.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.camera.view.CameraFragment
import co.tiagoaguiar.course.instagram.common.extension.replaceFragment
import co.tiagoaguiar.course.instagram.databinding.ActivityMainBinding
import co.tiagoaguiar.course.instagram.home.view.HomeFragment
import co.tiagoaguiar.course.instagram.profile.view.ProfileFragment
import co.tiagoaguiar.course.instagram.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    //fragmentos que serao utilizados
    private lateinit var homeFragment: Fragment
    private lateinit var searchFragment: Fragment
    private lateinit var cameraFragment: Fragment
    private lateinit var profileFragment: Fragment

    private var currentFragment: Fragment? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        Alterar a status bar programaticamente aos inves de alterar via Theme

        window é o container principal que inclui tambem a satusbar. Vamos mudar a cor do icones da status bar
        Vc considera que o tema claro ele entende que os icones devem ser escuros
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS) ou com let{}
*/
        //* acertos para rodar API 21 *
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
                window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
                //mudar os temas dos itens do sistema. window.insetsController é o controlador dos icones das coisas dentro
                //do sistema Android
            }
        }
        //Avisa Android que vai ter uma ToolBar e sera resposavel escutar eventos de ação nela
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        //instancia dos fragmentos
        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        profileFragment = ProfileFragment()
        cameraFragment = CameraFragment()

        //esta atividade que esta implementando o callback quando clicar no icone
        binding.mainButtonNav.setOnNavigationItemSelectedListener(this)
        binding.mainButtonNav.selectedItemId = R.id.menu_botton_home

    }
    //escutar o evento de click do botton Navigation, precisa de um listener na atividade principal
    //entao implementa ele na classe (this)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolBarEnable = false

        when (item.itemId) {
            R.id.menu_botton_home -> {
                //If para evitar varios click no icone empilhando o fragmento e impedindo o retorno
                if (currentFragment == homeFragment) return false
                currentFragment = homeFragment
            }
            R.id.menu_botton_search -> {
                if (currentFragment == searchFragment) return false
                currentFragment = searchFragment
            }
            R.id.menu_botton_add -> {
                if (currentFragment == cameraFragment) return false
                currentFragment = cameraFragment
            }
            R.id.menu_botton_profile -> {
                if (currentFragment == profileFragment) return false
                currentFragment = profileFragment
                scrollToolBarEnable = true
            }
        }
        setScrollToolbarEnable(scrollToolBarEnable)

        currentFragment?.let {
            replaceFragment(R.id.main_fragment, it)
        }
        return true
    }

    private fun setScrollToolbarEnable(scrollToolBarEnable: Boolean) {
        val params = binding.mainToolbar.layoutParams as
                   AppBarLayout.LayoutParams
        val coordinatParams = binding.mainAppBar.layoutParams as
                    CoordinatorLayout.LayoutParams
        if (scrollToolBarEnable) {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatParams.behavior = AppBarLayout.Behavior()
        } else {
            params.scrollFlags = 0
            coordinatParams.behavior = null
        }
        binding.mainAppBar.layoutParams = coordinatParams
    }
}