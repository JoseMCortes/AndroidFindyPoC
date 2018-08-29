package josecortes.com.baseproject.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import josecortes.com.baseproject.R

/**
 * Base Fragment in charge of dealing with the main Presenter interaction.
 * The Fragment provides basic common methods, such as error and dialog management.
 */
@Suppress("DEPRECATION")
abstract class BaseFragment<out P : BasePresenter<BaseView>> : BaseView, Fragment() {

    var dialog: ProgressDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getPresenter().dettachView()
    }

    override fun showError(error: String) {
        if (activity != null)
            Snackbar.make(activity!!.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show()
    }

    override fun showLoading() {
        dialog = ProgressDialog(activity)
        dialog?.setTitle(getString(R.string.loading_title))
        dialog?.setMessage(getString(R.string.loading_description))
        dialog?.setCancelable(false)
        dialog?.show()
    }

    override fun dismissLoading() {
        dialog?.dismiss()
    }

    /**
     * Instantiates the mPresenter the Activity is based on.
     */
    protected abstract fun getPresenter(): P

}