package josecortes.com.baseproject.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Custom Scope for Fragments
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {

}
