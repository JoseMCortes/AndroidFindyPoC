package josecortes.com.baseproject.services.items

import io.reactivex.Observable
import josecortes.com.baseproject.BuildConfig
import josecortes.com.baseproject.model.PoiList
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PoiService {

    companion object Factory {
        fun create(): PoiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(PoiService::class.java)

        }
    }

    @GET("/")
    fun getItems(@Query("lat1") p1Lat: String, @Query("lon1") p1Lon: String, @Query("lat2") p2Lat: String, @Query("lon2") p2Lon: String): Observable<PoiList>

}
