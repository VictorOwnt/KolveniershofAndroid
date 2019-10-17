package be.hogent.kolveniershof.base

import androidx.lifecycle.ViewModel
import be.hogent.kolveniershof.injection.component.DaggerViewModelInjectorComponent
import be.hogent.kolveniershof.injection.component.ViewModelInjectorComponent
import be.hogent.kolveniershof.injection.module.NetworkModule
import be.hogent.kolveniershof.viewmodels.DayViewModel
import be.hogent.kolveniershof.viewmodels.UserViewModel

abstract class BaseViewModel : ViewModel() {

    private val injectorComponent: ViewModelInjectorComponent =
        DaggerViewModelInjectorComponent
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is UserViewModel -> injectorComponent.inject(this)
            is DayViewModel -> injectorComponent.inject(this)
        }
    }

}