package josecortes.com.baseproject

import dagger.Module
import dagger.Provides
import dagger.Reusable
import josecortes.com.baseproject.services.items.PoiRepository
import org.mockito.Mockito

@Module
object TestRepositoryModule {

    @Provides
    @Reusable
    @JvmStatic
    fun providePoiRepository(): PoiRepository {
        return Mockito.mock(PoiRepository::class.java)
    }

}