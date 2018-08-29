package josecortes.com.baseproject.ui.itemlisting.listpois.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import josecortes.com.baseproject.R
import josecortes.com.baseproject.model.Poi
import kotlinx.android.synthetic.main.item_poi.view.*

/**
 * Adapter to show the list of Pois.
 * When clicking on a Poi, onPoiClicked callback will be invoked.
 */
class PoiListAdapter(
        pois: List<Poi>,
        private val listener: OnPoiClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listPois: List<Poi> = pois

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PoiViewHolder(layoutInflater.inflate(R.layout.item_poi, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val poi = listPois[position]
        (holder as PoiViewHolder).bind(poi)
        holder.itemView.setOnClickListener { listener.onPoiClicked(poi) }
    }

    override fun getItemCount() = listPois.size

    private class PoiViewHolder(poiView: View) : RecyclerView.ViewHolder(poiView) {

        private val nameView = poiView.name
        private val descriptionView = poiView.description
        private val image = poiView.icon

        fun bind(poi: Poi) {
            nameView.text = nameView.context.getString(R.string.title_you)

            if (poi.coordinate != null)
                descriptionView.text = nameView.context.getString(R.string.item_coordinates, poi.coordinate.latitude, poi.coordinate.longitude)
            else
                descriptionView.text = nameView.context.getString(R.string.invalid_poi)

            //TODO: add more cases if needed
            val resourceId = R.drawable.person
            image.setImageResource(resourceId)
        }

    }

    interface OnPoiClickListener {

        fun onPoiClicked(poi: Poi)

    }

}
