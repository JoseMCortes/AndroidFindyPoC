package josecortes.com.baseproject.ui.itemlisting

import com.nhaarman.mockito_kotlin.mock
import josecortes.com.baseproject.model.Poi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class MenuFragmentImplTest {

    lateinit var mainActivityPresenter: MainActivityPresenterImpl
    lateinit var mView: MainActivityPresenter.View


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        mView = mock()
        mainActivityPresenter = MainActivityPresenterImpl()
        mainActivityPresenter.attachView(mView)
    }

    @After
    fun cleanUp() {
    }

    @Test
    fun prepareView_showMenuScreen() {
        mainActivityPresenter.prepareView()
        verify(mView).showMenuScreen()
    }

    @Test
    fun prepareView_startTask() {
        mainActivityPresenter.startTask()
        verify(mView).showPoiListScreen()
    }

    @Test
    fun prepareView_poiSelected() {
        val poi = Poi(null, null, null)
        mainActivityPresenter.poiSelected(poi)
        verify(mView).showPoiMapScreen(poi)
    }

}