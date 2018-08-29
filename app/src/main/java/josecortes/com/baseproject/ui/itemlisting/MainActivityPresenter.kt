package josecortes.com.baseproject.ui.itemlisting

import josecortes.com.baseproject.base.BasePresenter
import josecortes.com.baseproject.base.BaseView
import josecortes.com.baseproject.model.Poi


abstract class MainActivityPresenter : BasePresenter<MainActivityPresenter.View>() {

    open fun prepareView() {}
    open fun startTask() {}
    open fun poiSelected(poiDetails: Poi) {}

    interface View : BaseView {
        fun showMenuScreen()
        fun showPoiListScreen()
        fun showPoiMapScreen(defaultPoi: Poi)
    }

}