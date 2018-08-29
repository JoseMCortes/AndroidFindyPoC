package josecortes.com.baseproject.ui.itemlisting.listpois

import android.content.Context
import josecortes.com.baseproject.base.BasePresenter
import josecortes.com.baseproject.base.BaseView
import josecortes.com.baseproject.model.Poi

abstract class PoiListFragmentPresenter : BasePresenter<PoiListFragmentPresenter.PresenterView>() {

    open fun prepareView(context: Context) {}

    interface PresenterView : BaseView {
        fun showListPois(pois: List<Poi>)
    }

}