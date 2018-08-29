package josecortes.com.baseproject.ui.itemlisting.listpois

import android.content.Context
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.services.ServiceCallback
import josecortes.com.baseproject.services.items.PoiRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


class PoiListFragmentPresenterWithoutDaggerImplTest {

    lateinit var mPoiListFragmentPresenter: PoiListFragmentPresenterImpl
    lateinit var mView: PoiListFragmentPresenter.PresenterView
    lateinit var poiRepository: PoiRepository
    lateinit var context: Context

    @Captor
    lateinit var dummyCallbackArgumentCaptor: ArgumentCaptor<ServiceCallback<List<Poi>?>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        poiRepository = Mockito.mock(PoiRepository::class.java)
        mView = mock()
        mPoiListFragmentPresenter = PoiListFragmentPresenterImpl(poiRepository)
        mPoiListFragmentPresenter.attachView(mView)
    }

    @After
    fun cleanUp() {
    }

    @Test
    fun prepareView_showListPois_success() {
        val pois = ArrayList<Poi>()
        mPoiListFragmentPresenter.prepareView(mock())
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onSuccess(pois)
        verify(mView).showListPois(pois)
        verify(mView).showLoading()
    }

    @Test
    fun prepareView_showListPois_empty() {
        context = Mockito.mock(Context::class.java)
        mPoiListFragmentPresenter.prepareView(context)
        Mockito.`when`(context.getString(ArgumentMatchers.anyInt())).thenReturn("Error")
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onSuccess(null)
        verify(mView).showError("Error")
        verify(mView).showLoading()
    }

    @Test
    fun prepareView_showListPois_fails() {
        context = Mockito.mock(Context::class.java)
        mPoiListFragmentPresenter.prepareView(context)
        Mockito.`when`(context.getString(ArgumentMatchers.anyInt())).thenReturn("Error")
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onError(mock())
        verify(mView).showError(ArgumentMatchers.anyString())
    }

    @Test
    fun prepareView_showListPois_any() {
        context = Mockito.mock(Context::class.java)
        mPoiListFragmentPresenter.prepareView(context)
        Mockito.verify(poiRepository).getAllPois(anyString(), anyString(), anyString(), anyString(), capture(dummyCallbackArgumentCaptor))
        dummyCallbackArgumentCaptor.value.onAny()
        verify(mView).showLoading()
    }

}