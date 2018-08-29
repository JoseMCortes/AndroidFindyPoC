package josecortes.com.baseproject.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Custom Scope for Activities
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {

}
