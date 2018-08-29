package josecortes.com.baseproject.services.items

import android.content.Context
import android.location.Geocoder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.services.ServiceCallback
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Class to interact with the API Poi Repository
 *
 * To keep it simple for the test direct RxJava communication is used,
 * adding Uses Cases with RxJava would be another feasible option.
 */
open class PoiRepository @Inject constructor() {

    var service = PoiService.Factory.create()

    /**
     * Given a bounding area, returns the list of Pois inside the square of the area
     * @param p1Lat First point latitude
     * @param p1Long First point longitude
     * @param p2Lat Second point latitude
     * @param p2Long Second point longitude
     * @param callback the callback to receive the items in case of success or errors in case of failure
     */
    open fun getAllPois(lat1: String, lon1: String, lat2: String, lon2: String, callback: ServiceCallback<List<Poi>?>) {
        service.getItems(lat1, lon1, lat2, lon2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            callback.onSuccess(result?.poiList)
                            callback.onAny()
                        },
                        { error ->
                            callback.onError(Exception(error))
                            callback.onAny()
                        })
    }

    /**
     * Given a position, returns the associated Address of the position (if any)
     */
    //TODO: we can use coroutines to make it nicer!
    open fun getAddress(context: Context, lat: Double, long: Double, callback: ServiceCallback<String>) {
        Thread().run {
            val geoCoder = Geocoder(
                    context, Locale.ENGLISH)
            try {
                val addresses = geoCoder.getFromLocation(lat,
                        long, 1)

                var lines = ""
                if (addresses.size > 0) {
                    for (i in 0 until addresses[0].maxAddressLineIndex + 1)
                        lines += addresses[0].getAddressLine(i) + "\n"
                }

                callback.onSuccess(lines)

            } catch (e: IOException) {
                callback.onError(e)
            }
        }
    }
}