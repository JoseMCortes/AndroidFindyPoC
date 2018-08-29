package josecortes.com.baseproject.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

/**
 * Model to encapsulate the list of Pois.
 * Classes have been market as @Parcel to allow easy parcel with Parceler library
 */
@Parcel(Parcel.Serialization.BEAN)
data class PoiList @ParcelConstructor constructor(val poiList: List<Poi>?)

@Parcel(Parcel.Serialization.BEAN)
data class Poi @ParcelConstructor constructor(
        val id: Int?,
        val coordinate: Coordinate?,
        val heading: Double?)

@Parcel(Parcel.Serialization.BEAN)
data class Coordinate @ParcelConstructor constructor(
        val latitude: Double?,
        val longitude: Double?)

