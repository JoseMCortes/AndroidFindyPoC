package josecortes.com.baseproject.ui.itemlisting.listpois

import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import josecortes.com.baseproject.DaggerTestRepositoryComponent
import josecortes.com.baseproject.TestRepositoryModule
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.services.ServiceCallback
import josecortes.com.baseproject.services.items.PoiRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import javax.inject.Inject

class PoiListFragmentPresenterWithDaggerImplTest {

    // Mock created by Dagger
    @Inject
    lateinit var poiRepository: PoiRepository
    lateinit var mPoiListFragmentPresenter: PoiListFragmentPresenterImpl

    // Mock created directly
    @Mock
    lateinit var mView: PoiListFragmentPresenter.PresenterView

    @Captor
    lateinit var dummyCallbackArgumentCaptor: ArgumentCaptor<ServiceCallback<List<Poi>?>>

    @Before
    fun setUp() {
        val component = DaggerTestRepositoryComponent.builder()
                .repositoryModule(TestRepositoryModule)
                .build()
        component.inject(this)
        MockitoAnnotations.initMocks(this)
        mPoiListFragmentPresenter = PoiListFragmentPresenterImpl(poiRepository)
        mPoiListFragmentPresenter.attachView(mView)
    }

    @After
    fun cleanUp() {

    }

    @Test
    fun prepareView_showListPois() {
        val pois = ArrayList<Poi>()
        // `when`(poiRepository.getAllPois(any()))
        mPoiListFragmentPresenter.prepareView(mock())

        //whenever
        Mockito.verify(poiRepository).getAllPois(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onSuccess(pois)
        Mockito.verify(mView).showListPois(pois)

    }


}