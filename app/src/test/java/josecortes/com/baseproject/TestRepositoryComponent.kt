package josecortes.com.baseproject

import dagger.Component
import josecortes.com.baseproject.ui.itemlisting.listpois.PoiListFragmentPresenterWithDaggerImplTest
import javax.inject.Singleton

/**
 * @author Jose Cortes
 * Useful component if needed to provide specific test behaviours
 */

@Singleton
@Component(modules = [(TestRepositoryModule::class)])
interface TestRepositoryComponent {

    @Component.Builder
    interface Builder {
        fun build(): TestRepositoryComponent
        fun repositoryModule(repositoryModule: TestRepositoryModule): Builder
    }

    // Register here presenters needing this Component
    fun inject(presenter: PoiListFragmentPresenterWithDaggerImplTest)

}