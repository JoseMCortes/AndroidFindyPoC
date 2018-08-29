package josecortes.com.baseproject.ui.itemlisting.menu

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


class MenuFragmentImplTest {

    lateinit var menuFragmentPresenter: MenuFragmentPresenterImpl
    lateinit var mView: MenuFragmentPresenter.View


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        mView = mock()
        menuFragmentPresenter = MenuFragmentPresenterImpl()
        menuFragmentPresenter.attachView(mView)
    }

    @After
    fun cleanUp() {
    }

    @Test
    fun startTaskSelected_startTask() {
        menuFragmentPresenter.startTaskSelected()
        verify(mView).startTask()
    }

}