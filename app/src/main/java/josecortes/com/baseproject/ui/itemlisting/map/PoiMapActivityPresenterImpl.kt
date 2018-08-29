package josecortes.com.baseproject.ui.itemlisting.map


import android.app.Activity
import android.content.Context
import com.google.android.gms.maps.model.Marker
import josecortes.com.baseproject.R
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.services.ServiceCallback
import josecortes.com.baseproject.services.items.PoiRepository
import josecortes.com.baseproject.ui.itemmaping.map.PoiMapActivityPresenter
import javax.inject.Inject

/**
 * Presenter implementation for PoiMap
 */
class PoiMapActivityPresenterImpl @Inject constructor(val poiRepository: PoiRepository) : PoiMapActivityPresenter() {

    var defaultPoi: Poi? = null

    /**
     * Initialization method with a default Poi
     * @param poi the default Poi
     * */
    override fun prepareView(poi: Poi) {
        this.defaultPoi = poi
        getView()?.showMapScreen()
    }

    /**
     * When the map is ready and there is a defaultPoi, center the map on it.
     */
    override fun onMapReady() {
        if (defaultPoi != null)
            getView()?.showDefaultPoi(defaultPoi!!)
    }

    /**
     * Given a Square created by the bounding limits of two points, returns the list of Pois inside the square
     *
     * @param p1Lat first point latitude
     * @param p1Long first point longitude
     * @param p2Lat second point latitude
     * @para p2Long second point longitude
     */
    override fun updateMapBounds(p1Lat: Double?, p1Long: Double?, p2Lat: Double?, p2Long: Double?, context: Context) {
        if (p1Lat == null || p1Long == null || p2Lat == null || p2Long == null) {
            getView()?.showError(context.getString(R.string.error_showing_map))
        } else {
            getView()?.showLoading()
            poiRepository.getAllPois(p1Lat.toString(), p1Long.toString(), p2Lat.toString(), p2Long.toString(), object : ServiceCallback<List<Poi>?> {
                override fun onSuccess(t: List<Poi>?) {
                    if (t != null)
                        getView()?.showPoisInMap(defaultPoi, t)
                    else
                        getView()?.showError(context.getString(R.string.error_showing_map))
                }

                override fun onAny() {
                    getView()?.dismissLoading()
                }

                override fun onError(exception: Exception?) {
                    getView()?.showError(exception?.message
                            ?: context.getString(R.string.error_showing_map))
                }
            })
        }
    }

    /**
     * Given a marker, returns the associated Address (if found)
     */
    override fun retrieveAddressForMarker(activity: Activity, marker: Marker?) {
        val lat = marker?.position?.latitude
        val long = marker?.position?.longitude
        if (lat == null || long == null) {
            getView()?.showAddressForMarker(activity.getString(R.string.error_unresolved_address), marker)
        } else {
            poiRepository.getAddress(activity, lat, long, object : ServiceCallback<String> {
                override fun onSuccess(t: String) {
                    activity.runOnUiThread {
                        if (t.trim().isEmpty()) {
                            getView()?.showAddressForMarker(activity.getString(R.string.error_unresolved_address), marker)
                        } else {
                            getView()?.showAddressForMarker(t, marker)
                        }
                    }
                }

                override fun onAny() {
                }

                override fun onError(exception: Exception?) {
                    activity.runOnUiThread {
                        getView()?.showAddressForMarker(activity.getString(R.string.error_unresolved_address), marker)
                    }
                }
            })
        }


    }


}