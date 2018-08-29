package josecortes.com.baseproject.base

/**
 * Base mPresenter any mPresenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the mPresenter is based on
 * @constructor Injects the required dependencies
 */
@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<out V : BaseView> {

    private var view: V? = null

    /**
     * This method may be called when the view is destroyed/dettached
     */
    open fun dettachView() {
        view = null
    }

    /**
     * This method may be called when the view is created/attached
     */
    open fun attachView(view: BaseView?) {
        this.view = view as V
    }

    /**
     * Method to return the View. If the View have been dettached from the Presenter it will be null
     */
    fun getView(): V? {
        return view
    }

}