package josecortes.com.baseproject.injection.module


import dagger.Module
import dagger.Provides
import dagger.Reusable
import josecortes.com.baseproject.services.items.PoiRepository

@Module
object RepositoryModule {

    /**
     * Provides instances of PoiRepository.
     * @Reusable binding will allow to reuse the instance when possible and create a new
     * instance when necessary for @Scope scopes (ActivityScope and FragmentScope for the test),
     * allowing the object to be GC as soon as possible.
     * @Singleton binding would also work
     */
    @Provides
    @Reusable
    @JvmStatic
    fun providePoiRepository(): PoiRepository {
        return PoiRepository()
    }

}