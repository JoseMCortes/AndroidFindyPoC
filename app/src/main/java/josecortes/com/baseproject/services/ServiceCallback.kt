package josecortes.com.baseproject.services

/**
 * Common contract to be followed by new Services
 */
interface ServiceCallback<T> {

    fun onSuccess(t: T)
    fun onError(exception: Exception?)
    fun onAny()

}