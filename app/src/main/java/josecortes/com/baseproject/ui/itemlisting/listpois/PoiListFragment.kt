package josecortes.com.baseproject.ui.itemlisting.listpois

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Component
import josecortes.com.baseproject.R
import josecortes.com.baseproject.base.BaseFragment
import josecortes.com.baseproject.injection.FragmentScope
import josecortes.com.baseproject.injection.component.DaggerRepositoryComponent
import josecortes.com.baseproject.injection.component.RepositoryComponent
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.ui.itemlisting.listpois.adapter.PoiListAdapter
import kotlinx.android.synthetic.main.fragment_poi_list.*
import javax.inject.Inject

/**
 * Fragment representing the screen containing the list of Pois
 */
class PoiListFragment : BaseFragment<PoiListFragmentPresenter>(), PoiListFragmentPresenter.PresenterView, PoiListAdapter.OnPoiClickListener {

    @Inject
    internal lateinit var mPresenter: PoiListFragmentPresenterImpl

    var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerPoiListFragment_PoiListFragmentComponent.builder()
                .repositoryComponent(DaggerRepositoryComponent.builder().build())
                .build()
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.callback = context as Callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_poi_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.title_london_pois)
        mPresenter.prepareView(context!!)
        list_pois?.layoutManager = LinearLayoutManager(context)
    }

    override fun getPresenter(): PoiListFragmentPresenter {
        return mPresenter
    }

    companion object {

        fun newInstance() = PoiListFragment()
    }

    /**
     * Shows the list of Pois in the RecyclerView
     * @param pois the list of Pois
     */
    override fun showListPois(pois: List<Poi>) {
        list_pois?.adapter = PoiListAdapter(pois, this)
    }

    /**
     * Method executed when a item on the list (poi) has been clicked
     * @param poi The selected poi
     */
    override fun onPoiClicked(poi: Poi) {
        callback?.showPoiDetails(poi)
    }

    interface Callback {

        fun showPoiDetails(poiDetails: Poi)

    }

    /**
     * Creates the specific component for this Fragment and references the RepositoryComponent
     */
    @FragmentScope
    @Component(dependencies = arrayOf(RepositoryComponent::class))
    interface PoiListFragmentComponent {

        fun inject(fragment: PoiListFragment)

    }

}