package br.com.github.dimrsilva.giphy

import android.app.Application
import br.com.github.dimrsilva.giphy.application.applicationModules
import br.com.github.dimrsilva.giphy.presentation.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GifsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(applicationModules)
            modules(presentationModules)
        }
    }
}