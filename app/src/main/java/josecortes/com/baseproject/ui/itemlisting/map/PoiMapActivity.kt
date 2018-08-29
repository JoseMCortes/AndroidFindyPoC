package josecortes.com.baseproject.ui.itemlisting.map

import android.graphics.*
import android.os.Bundle
import android.support.annotation.DrawableRes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.Component
import josecortes.com.baseproject.R
import josecortes.com.baseproject.base.BaseActivity
import josecortes.com.baseproject.injection.ActivityScope
import josecortes.com.baseproject.injection.component.DaggerRepositoryComponent
import josecortes.com.baseproject.injection.component.RepositoryComponent
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.ui.itemmaping.map.PoiMapActivityPresenter
import kotlinx.android.synthetic.main.fragment_poi_map.*
import org.parceler.Parcels
import javax.inject.Inject


/**
 * Activity to show a list of Pois in a GMaps map
 */
class PoiMapActivity : BaseActivity<PoiMapActivityPresenter>(), PoiMapActivityPresenter.View, OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnMarkerClickListener {
    @Inject
    internal lateinit var mPresenter: PoiMapActivityPresenterImpl
    private var map: GoogleMap? = null
    private var cameraMoveType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerPoiMapActivity_PoiMapActivityComponent.builder()
                .repositoryComponent(DaggerRepositoryComponent.builder().build())
                .build()
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        setSupportActionBar(toolbar)

        // Retrieve the default Poi to highlight (in case of previous selection) or from the
        // saveInstanceState in case of Activity recreation
        val defaultPoi = Parcels.unwrap<Poi>(intent.extras.getParcelable(EXTRA_ITEM))
                ?: Parcels.unwrap<Poi>(savedInstanceState?.getParcelable(EXTRA_ITEM))
        mPresenter.prepareView(defaultPoi)

    }

    /**
     * Opens the screen (MapFragment) to perform map operations and interactions
     */
    override fun showMapScreen() {
        val supportMapFragment = SupportMapFragment.newInstance()
        supportMapFragment.getMapAsync(this)
        supportFragmentManager.beginTransaction().replace(R.id.content, supportMapFragment, SupportMapFragment::class.java.simpleName)
                .commit()
    }

    /**
     *  Callback method invoked when the map is ready to interact with
     */
    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.setOnCameraIdleListener(this)
        map?.setOnCameraMoveStartedListener(this)
        map?.setOnMarkerClickListener(this)
        if (checkPermission())
            map?.isMyLocationEnabled = true
        mPresenter.onMapReady()
    }

    /**
     * Given a Poi, centers the map in that Poi (zoom = 7)
     */
    override fun showDefaultPoi(poi: Poi) {
        map?.clear()
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(poi.coordinate!!.latitude!!, poi.coordinate.longitude!!), 7.0f))
    }

    /**
     * Callback method invoked when the map stops moving. It can be performed from a manual gesture
     * either an automatic move (i.e. when clicking on a Poi).
     *
     */
    override fun onCameraIdle() {
        // To improve the UX, do only reset the map when is a gesture. In case of clicking on a marker
        // do not reset the map to avoid disappearing the clicked point.

        if (cameraMoveType != GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION) {
            val bounds = map?.projection?.visibleRegion?.latLngBounds
            mPresenter.updateMapBounds(bounds?.northeast?.latitude, bounds?.northeast?.longitude, bounds?.southwest?.latitude, bounds?.southwest?.longitude, this)
        }
    }

    /**
     * Given a list of Pois and a default Poi, it draws them on the Map
     *
     * @param defaultPoi The default Poi, it will be highlighted on the map
     * @param pois The list of Pois to draw on the map
     */
    override fun showPoisInMap(defaultPoi: Poi?, pois: List<Poi>) {

        map?.clear()

        if (defaultPoi != null) {
            addPoiToMap(defaultPoi, true)
        }
        for (poi in pois) {
            addPoiToMap(poi, false)
        }
    }

    /**
     * Painst a poi on the map using the type of Poi to decide the icon.
     * @param isDefault If true, it draws the Poi as highlighted (in red)
     */
    private fun addPoiToMap(poi: Poi, isDefault: Boolean) {

        // Create the marker
        val marker = map?.addMarker(MarkerOptions()
                .position(LatLng(poi.coordinate!!.latitude!!, poi.coordinate.longitude!!))
                .title(getString(R.string.title_you)))

        // Choose the icon depending on the Fleet type
        val resourceId = R.drawable.person

        // Paint the default icon, if needed
        var paint: Paint? = null
        if (isDefault) {
            paint = Paint()
            paint.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        }

        // Draw the bitmap
        val bitmap = createSingleImageFromMultipleImages(
                resizeMapIcons(resourceId, 100, 100),
                resizeMapIcons(R.drawable.arrow_up, 100, 100), poi.heading, paint)

        marker?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    /**
     * Given two images, rotates the second one "heading" degrees and overlaps them in order
     * to create a single one.
     * @param firstImage first Bitmap image
     * @param secondImage second Bitmap image
     * @param heading degrees used to rotate the second image (0...360)
     *
     *  The idea after that is to use the heading degrees to show a
     *  small arrow pointing the current orientation of the person. This way we can show the
     *  person position but also the orientation.
     */
    private fun createSingleImageFromMultipleImages(firstImage: Bitmap, secondImage: Bitmap, heading: Double?, paint: Paint?): Bitmap {
        val result = Bitmap.createBitmap(100, 100, firstImage.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(firstImage, 0f, 0f, paint)
        val matrix = Matrix()
        matrix.setRotate(heading?.toFloat() ?: 0.0f, 100f / 2, 100f / 2)
        canvas.drawBitmap(secondImage, matrix, paint)
        return result
    }

    /**
     * Given a resource Id, scalates it to the specified size.
     *
     * @param iconId The drawableId
     * @param width the new icon width
     * @param height the new image height
     */
    fun resizeMapIcons(@DrawableRes iconId: Int, width: Int, height: Int): Bitmap {
        val imageBitmap = BitmapFactory.decodeResource(resources, iconId)
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }

    /**
     * Callback method, sets the type of move the camera is performing
     * @param moveType the type (1 gesture, 2 animation)
     */
    override fun onCameraMoveStarted(moveType: Int) {
        cameraMoveType = moveType
    }

    /**
     * Method executed when a Marker is clicked
     * @param marker The marker
     */
    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.snippet = getString(R.string.marker_loading_address)
        mPresenter.retrieveAddressForMarker(this, marker)
        return false
    }

    /**
     * Method executed when a new Address has been found (from GeoLoc) for this Marker.
     * @param address the new Address
     * @param marker the marker
     */
    override fun showAddressForMarker(address: String, marker: Marker?) {
        marker?.snippet = address
    }

    override fun getPresenter(): PoiMapActivityPresenter {
        return mPresenter
    }

    companion object {

        private const val EXTRA_ITEM = "key:item"

        fun bundleWith(poi: Poi): Bundle {
            val b = Bundle()
            b.putParcelable(EXTRA_ITEM, Parcels.wrap(poi))
            return b
        }

    }

    /**
     * Creates the specific component for this Activity and references the RepositoryComponent
     */
    @ActivityScope
    @Component(dependencies = [(RepositoryComponent::class)])
    interface PoiMapActivityComponent {

        fun inject(activity: PoiMapActivity)

    }


}
