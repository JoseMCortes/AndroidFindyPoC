package josecortes.com.baseproject.ui.itemlisting.menu

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
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.android.synthetic.main.fragment_poi_list.*
import javax.inject.Inject

/**
 * Fragment representing the Main App Screen
 */
class MenuFragment : BaseFragment<MenuFragmentPresenter>(), MenuFragmentPresenter.View {

    @Inject
    internal lateinit var mPresenter: MenuFragmentPresenterImpl

    var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val component = DaggerMenuFragment_MenuFragmentComponent.builder()
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
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_pois?.layoutManager = LinearLayoutManager(context)
        task.setOnClickListener({ getPresenter().startTaskSelected() })
    }

    override fun startTask() {
        callback?.startTask()
    }

    override fun getPresenter(): MenuFragmentPresenter {
        return mPresenter
    }

    companion object {

        fun newInstance() = MenuFragment()
    }

    interface Callback {
        fun startTask()
    }

    /**
     * Creates the specific component for this Fragment and references the RepositoryComponent
     */
    @FragmentScope
    @Component(dependencies = arrayOf(RepositoryComponent::class))
    interface MenuFragmentComponent {

        fun inject(fragment: MenuFragment)

    }

}