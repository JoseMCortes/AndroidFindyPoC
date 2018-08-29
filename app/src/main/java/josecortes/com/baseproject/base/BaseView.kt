package josecortes.com.baseproject.base

/**
 * Base view any view must implement.
 */
interface BaseView {
    fun showError(error: String)
    fun showLoading()
    fun dismissLoading()
}