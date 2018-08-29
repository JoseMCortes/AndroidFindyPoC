package josecortes.com.baseproject.ui.itemlisting.menu

import javax.inject.Inject

class MenuFragmentPresenterImpl @Inject constructor() : MenuFragmentPresenter() {

    override fun startTaskSelected() {
        getView()?.startTask()
    }

}