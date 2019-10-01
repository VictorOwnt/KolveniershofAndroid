package be.hogent.kolveniershof.injection.component

import be.hogent.kolveniershof.injection.module.NetworkModule
import be.hogent.kolveniershof.viewmodels.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjectorComponent {

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder
    }

}