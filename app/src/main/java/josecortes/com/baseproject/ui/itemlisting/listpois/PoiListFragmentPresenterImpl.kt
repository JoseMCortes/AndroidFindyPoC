package josecortes.com.baseproject.ui.itemlisting.listpois

import android.content.Context
import josecortes.com.baseproject.R
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.services.ServiceCallback
import josecortes.com.baseproject.services.items.PoiRepository
import javax.inject.Inject

class PoiListFragmentPresenterImpl @Inject constructor(val poiRepository: PoiRepository) : PoiListFragmentPresenter() {

    companion object {
        const val LONDONLAT1 = "51.6074"
        const val LONDONLON1 = "0.2278"
        const val LONDONLAT2 = "51.4074"
        const val LONDONLON2 = "0.0278"
    }

    /**
     * Retrieve the list of Poins for the Hamburg coordinates
     */
    override fun prepareView(context: Context) {
        getView()?.showLoading()
        poiRepository.getAllPois(LONDONLAT1, LONDONLON1, LONDONLAT2, LONDONLON2, object : ServiceCallback<List<Poi>?> {
            override fun onSuccess(t: List<Poi>?) {
                if (t != null)
                    getView()?.showListPois(t)
                else
                    getView()?.showError(context.getString(R.string.error_retrieving_poi_list))
            }

            override fun onAny() {
                getView()?.dismissLoading()
            }

            override fun onError(exception: Exception?) {
                getView()?.showError(context.getString(R.string.error_retrieving_poi_list))
            }
        })
    }


}