package josecortes.com.baseproject.ui.itemlisting

import android.content.Intent
import android.os.Bundle
import dagger.Component
import josecortes.com.baseproject.R
import josecortes.com.baseproject.base.BaseActivity
import josecortes.com.baseproject.injection.ActivityScope
import josecortes.com.baseproject.injection.component.DaggerRepositoryComponent
import josecortes.com.baseproject.injection.component.RepositoryComponent
import josecortes.com.baseproject.model.Poi
import josecortes.com.baseproject.ui.itemlisting.listpois.PoiListFragment
import josecortes.com.baseproject.ui.itemlisting.map.PoiMapActivity
import josecortes.com.baseproject.ui.itemlisting.menu.MenuFragment
import kotlinx.android.synthetic.main.fragment_poi_list.*
import javax.inject.Inject

/**
 * Activity in charge of controlly the Main flow.
 * for this case there is only two screens (MenuFragment and PoiListFragment), this Activity should control
 */
class MainActivity : BaseActivity<MainActivityPresenter>(), MainActivityPresenter.View, PoiListFragment.Callback, MenuFragment.Callback {

    @Inject
    internal lateinit var mPresenter: MainActivityPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerMainActivity_MainActivityComponent.builder()
                .repositoryComponent(DaggerRepositoryComponent.builder().build())
                .build()
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        mPresenter.prepareView()

        checkPermission()
    }

    /**
     * Shows the main screen (MenuFragment in this case)
     */
    override fun showMenuScreen() {
        supportFragmentManager.beginTransaction().replace(R.id.content, MenuFragment.newInstance(), PoiListFragment::class.java.simpleName).commit()
    }

    /**
     * The user selected the start task button from the main screen (MenuFragment in this case)
     */
    override fun startTask() {
        getPresenter().startTask()
    }

    /**
     * Given a poi, shows the details (in this case opens the Map with the selected
     */
    override fun showPoiDetails(poiDetails: Poi) {
        mPresenter.poiSelected(poiDetails)
    }

    /**
     * Shows the Map of Pois with the selected Poi as a default
     */
    override fun showPoiMapScreen(defaultPoi: Poi) {
        val intent = Intent(this, PoiMapActivity::class.java)
        intent.putExtras(PoiMapActivity.Companion.bundleWith(defaultPoi))
        startActivity(intent)
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    /**
     * Shows the List of Pois screen
     */
    override fun showPoiListScreen() {
        supportFragmentManager.beginTransaction().replace(R.id.content, PoiListFragment.newInstance(), PoiListFragment::class.java.simpleName).commit()
    }

    override fun getPresenter(): MainActivityPresenter {
        return mPresenter
    }

    @ActivityScope
    @Component(dependencies = arrayOf(RepositoryComponent::class))
    interface MainActivityComponent {

        fun inject(activity: MainActivity)

    }


}
