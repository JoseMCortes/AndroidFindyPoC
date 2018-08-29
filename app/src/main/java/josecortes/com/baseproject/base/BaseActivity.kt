package josecortes.com.baseproject.base

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import josecortes.com.baseproject.R

@Suppress("DEPRECATION")

/**
 * Base Activity in charge of dealing with the main Presenter interaction
 * The Activity provides basic common methods, such as error and dialog management.
 */
abstract class BaseActivity<out P : BasePresenter<BaseView>> : BaseView, AppCompatActivity() {

    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenter().attachView(this)
    }

    /**
     * View attached to presenter when the Activity is visible
     */
    override fun onStart() {
        super.onStart()
        getPresenter().attachView(this)
    }

    /**
     * View dettached from presenter when the Activity is not longer visible. It will avoid
     * the presenter to perform UI operations when UI is not longer existing.
     */
    override fun onStop() {
        super.onStop()
        getPresenter().dettachView()
    }

    override fun showError(error: String) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show()
    }

    override fun showLoading() {
        dialog = ProgressDialog(this)
        dialog?.setTitle(getString(R.string.loading_title))
        dialog?.setMessage(getString(R.string.loading_description))
        dialog?.setCancelable(false)
        dialog?.show()
    }

    override fun dismissLoading() {
        dialog?.dismiss()
    }

    fun checkPermission(): Boolean {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return true
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
    }
    
    /**
     * Instantiates the mPresenter the Activity is based on.
     */
    protected abstract fun getPresenter(): P

}