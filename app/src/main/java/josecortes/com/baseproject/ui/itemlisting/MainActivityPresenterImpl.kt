package josecortes.com.baseproject.ui.itemlisting


import josecortes.com.baseproject.model.Poi
import javax.inject.Inject

class MainActivityPresenterImpl @Inject constructor() : MainActivityPresenter() {

    override fun prepareView() {
        getView()?.showMenuScreen()
    }

    override fun startTask() {
        getView()?.showPoiListScreen()
    }

    override fun poiSelected(poiDetails: Poi) {
        getView()?.showPoiMapScreen(poiDetails)
    }


}