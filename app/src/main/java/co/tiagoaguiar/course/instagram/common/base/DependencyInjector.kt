package co.tiagoaguiar.course.instagram.common.base

import co.tiagoaguiar.course.instagram.login.data.FakeDataSource
import co.tiagoaguiar.course.instagram.login.data.LoginRepository
import co.tiagoaguiar.course.instagram.profile.data.*
import co.tiagoaguiar.course.instagram.register.data.FakeRegisterDataSource
import co.tiagoaguiar.course.instagram.register.data.RegisterRepository
import co.tiagoaguiar.course.instagram.splash.data.FakeLocalDataSource
import co.tiagoaguiar.course.instagram.splash.data.SplashRepository

// Injetor de dependencia manual ao inves de usar as bibliotecas de injeção de dep.

object DependencyInjector {
    fun loginRepository() : LoginRepository {
        return LoginRepository (FakeDataSource())
    }
    fun splashRepository() : SplashRepository {
        return SplashRepository (FakeLocalDataSource())
    }
    fun registerEmailRepository() : RegisterRepository {
        return RegisterRepository (FakeRegisterDataSource())
    }
    fun profileRepository() : ProfileRepository {
        return ProfileRepository (ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }
}