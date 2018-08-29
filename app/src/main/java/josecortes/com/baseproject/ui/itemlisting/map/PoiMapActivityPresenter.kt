package josecortes.com.baseproject.ui.itemmaping.map

import android.app.Activity
import android.content.Context
import com.google.android.gms.maps.model.Marker
import josecortes.com.baseproject.base.BasePresenter
import josecortes.com.baseproject.base.BaseView
import josecortes.com.baseproject.model.Poi

/**
 * Methods to be implemented by the View and the Presenter for PoiMapActivity
 */
abstract class PoiMapActivityPresenter : BasePresenter<PoiMapActivityPresenter.View>() {

    open fun prepareView(poi: Poi) {}
    open fun updateMapBounds(p1Lat: Double?, p1Long: Double?, p2Lat: Double?, p2Long: Double?, context: Context) {}
    open fun onMapReady() {}
    open fun retrieveAddressForMarker(activity: Activity, marker: Marker?) {}

    interface View : BaseView {
        fun showMapScreen()
        fun showPoisInMap(defaultPoi: Poi?, pois: List<Poi>)
        fun showDefaultPoi(poi: Poi)
        fun showAddressForMarker(address: String, marker: Marker?)
    }

}