package josecortes.com.baseproject.injection.component

import dagger.Component
import josecortes.com.baseproject.injection.module.RepositoryModule
import javax.inject.Singleton

/**
 * Component providing the modules related to repositories (in this case only a single one)
 * */

@Singleton
@Component(modules = [(RepositoryModule::class)])
interface RepositoryComponent {

    @Component.Builder
    interface Builder {
        fun build(): RepositoryComponent
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
    }

}