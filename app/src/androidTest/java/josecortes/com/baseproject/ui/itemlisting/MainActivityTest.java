package josecortes.com.baseproject.ui.itemlisting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import josecortes.com.baseproject.R;
import josecortes.com.baseproject.ui.TestUtil;
import josecortes.com.baseproject.ui.itemlisting.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final Intent MY_ACTIVITY_INTENT = new Intent(
            InstrumentationRegistry.getTargetContext(), MainActivity.class);
    private static final int TIMEOUT = 5000;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        mActivityRule.launchActivity(MY_ACTIVITY_INTENT);
    }

    @Test
    public void ensureTextChangesWork() {
        onView(withId(R.id.task)).perform(click());
        MainActivityIdlingResource idlingResource = new MainActivityIdlingResource(
                mActivityRule.getActivity());

        // Wait until condition is reached as there is async operations in the background
        Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.list_pois))
                .check(matches(
                        TestUtil.atPosition(1, hasDescendant(withSubstring("Location")))));

    }

}