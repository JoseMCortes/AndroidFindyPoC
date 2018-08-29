package josecortes.com.baseproject.ui.itemlisting;


import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.RecyclerView;

import josecortes.com.baseproject.R;

public class MainActivityIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private boolean isIdle;
    private MainActivity activity;

    public MainActivityIdlingResource(final MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return MainActivityIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) {
            return true;
        }

        if (this.activity == null) {
            return false;
        }

        isIdle = ((RecyclerView) activity.findViewById(R.id.list_pois)).getAdapter() != null;
        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(
            ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}