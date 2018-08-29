package josecortes.com.baseproject.ui.itemlisting.menu

import josecortes.com.baseproject.base.BasePresenter
import josecortes.com.baseproject.base.BaseView

/**
 * Methods to be implemented by the View and the Presenter for MenuFragment
 */
abstract class MenuFragmentPresenter : BasePresenter<MenuFragmentPresenter.View>() {

    open fun startTaskSelected() {}

    interface View : BaseView {
        fun startTask()
    }

}