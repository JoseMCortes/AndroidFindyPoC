package josecortes.com.baseproject.ui.itemlisting.map

import android.content.Context
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.services.ServiceCallback
import josecortes.com.baseproject.services.items.PoiRepository
import josecortes.com.baseproject.ui.itemmaping.map.PoiMapActivityPresenter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify


class PoiListFragmentPresenterWithoutDaggerImplTest {

    lateinit var poiMapActivityPresenter: PoiMapActivityPresenterImpl
    lateinit var mView: PoiMapActivityPresenter.View
    lateinit var poiRepository: PoiRepository
    lateinit var context: Context

    @Captor
    lateinit var dummyCallbackArgumentCaptor: ArgumentCaptor<ServiceCallback<List<Poi>?>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        poiRepository = Mockito.mock(PoiRepository::class.java)
        mView = mock()
        poiMapActivityPresenter = PoiMapActivityPresenterImpl(poiRepository)
        poiMapActivityPresenter.attachView(mView)
    }

    @After
    fun cleanUp() {
    }

    @Test
    fun prepareView_showMapScreen() {
        val poi = Poi(null, null, null)
        poiMapActivityPresenter.prepareView(poi)
        verify(mView).showMapScreen()
    }

    @Test
    fun onMapReady_showDefaultPoi() {
        val poi = Poi(null, null, null)
        poiMapActivityPresenter.prepareView(poi)
        poiMapActivityPresenter.onMapReady()
        verify(mView).showDefaultPoi(poi)
    }

    @Test
    fun updateMapBounds_showPoisInMap() {
        val poi = Poi(null, null, null)
        val pois = ArrayList<Poi>()
        context = Mockito.mock(Context::class.java)
        poiMapActivityPresenter.prepareView(poi)
        poiMapActivityPresenter.updateMapBounds(1.0, 1.0, 1.0, 1.0, context)
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onSuccess(pois)
        verify(mView).showPoisInMap(poi, pois)
        verify(mView).showLoading()
    }

    @Test
    fun updateMapBounds_showErrorNoPois() {
        context = Mockito.mock(Context::class.java)
        poiMapActivityPresenter.updateMapBounds(1.0, 1.0, 1.0, 1.0, context)
        Mockito.`when`(context.getString(ArgumentMatchers.anyInt())).thenReturn("Error")
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onSuccess(null)
        verify(mView).showError(ArgumentMatchers.anyString())
        verify(mView).showLoading()
    }

    @Test
    fun updateMapBounds_showErrorException() {
        context = Mockito.mock(Context::class.java)
        poiMapActivityPresenter.updateMapBounds(1.0, 1.0, 1.0, 1.0, context)
        Mockito.`when`(context.getString(ArgumentMatchers.anyInt())).thenReturn("Error")
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onError(null)
        verify(mView).showError(ArgumentMatchers.anyString())
        verify(mView).showLoading()
    }

    @Test
    fun updateMapBounds_noLatitudes_showError() {
        context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getString(ArgumentMatchers.anyInt())).thenReturn("Error")
        poiMapActivityPresenter.updateMapBounds(null, 1.0, null, 1.0, context)
        verify(mView).showError(ArgumentMatchers.anyString())
    }
    @Test
    fun updateMapBounds_noLongitudes_showError() {
        context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getString(ArgumentMatchers.anyInt())).thenReturn("Error")
        poiMapActivityPresenter.updateMapBounds(1.0, null, 1.0, null, context)
        verify(mView).showError(ArgumentMatchers.anyString())
    }

}