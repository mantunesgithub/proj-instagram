package co.tiagoaguiar.course.instagram.main.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.extension.replaceFragment
import co.tiagoaguiar.course.instagram.databinding.ActivityMainBinding
import co.tiagoaguiar.course.instagram.home.view.HomeFragment
import co.tiagoaguiar.course.instagram.main.LogoutListener
import co.tiagoaguiar.course.instagram.post.view.AddFragment
import co.tiagoaguiar.course.instagram.profile.view.ProfileFragment
import co.tiagoaguiar.course.instagram.search.view.SearchFragment
import co.tiagoaguiar.course.instagram.splash.view.SplashActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    AddFragment.AddListener,
    SearchFragment.SearchListener,
    LogoutListener,
    ProfileFragment.FollowListener{

    private lateinit var binding: ActivityMainBinding
    //fragmentos que serao utilizados
    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: Fragment
    private lateinit var addFragment: Fragment
    private lateinit var profileFragment: ProfileFragment

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

            when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)){
                Configuration.UI_MODE_NIGHT_YES -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                    binding.mainImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )
                        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
                //mudar os temas dos itens do sistema. window.insetsController é o controlador dos icones das coisas dentro
                //do sistema Android
                }
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
        addFragment = AddFragment()

        //esta atividade que esta implementando o callback quando clicar no icone
        binding.mainButtonNav.setOnNavigationItemSelectedListener(this)
        binding.mainButtonNav.selectedItemId = R.id.menu_botton_home

    }

    override fun goToProfile(uuid: String) {

        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ProfileFragment.KEY_USER_ID, uuid)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment, fragment.javaClass.simpleName + "detail")
            addToBackStack(null)
            commit()
        }
    }

    override fun followUpdated() {
        homeFragment.presenter.clear()
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) {
            profileFragment.presenter.clear()
        }

    }
    override fun logout() {
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) {
            profileFragment.presenter.clear()
        }

        homeFragment.presenter.clear()
        homeFragment.presenter.logout()
        val intent = Intent(baseContext, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
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
                scrollToolBarEnable = false
            }
            R.id.menu_botton_add -> {
                if (currentFragment == addFragment) return false
                currentFragment = addFragment
                scrollToolBarEnable = false
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
    override fun onPostCreated() {
        homeFragment.presenter.clear()
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null)
            profileFragment.presenter.clear()

        binding.mainButtonNav.selectedItemId = R.id.menu_botton_home
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