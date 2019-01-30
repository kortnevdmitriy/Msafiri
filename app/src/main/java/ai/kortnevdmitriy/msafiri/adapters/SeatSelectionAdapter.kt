package ai.kortnevdmitriy.msafiri.adapters

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.utilities.Item
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

/**
 * Created by kortn on 11/21/2017.
 */

class SeatSelectionAdapter(
	internal var context: Context,
	private var layoutResourceId: Int,
	data: ArrayList<Item>
) : ArrayAdapter<Item>(context, layoutResourceId, data) {
	internal var data = ArrayList<Item>()
	
	init {
		this.data = data
	}
	
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		var row = convertView
		var holder: RecordHolder? = null
		
		try {
			if (row == null) {
				val inflater = (context as Activity).layoutInflater
				row = inflater.inflate(layoutResourceId, parent, false)
				
				holder = RecordHolder()
				holder.txtTitle = row!!.findViewById(R.id.item_text)
				holder.imageItem = row.findViewById(R.id.item_image)
				row.tag = holder
			} else {
				holder = row.tag as RecordHolder
			}
			
			val item = data[position]
			holder.txtTitle!!.text = item.getTitle()
			holder.imageItem!!.setImageBitmap(item.getImage())
		} catch (e: Exception) {
			e.printStackTrace()
		}
		
		return row!!
	}
	
	class RecordHolder {
		var txtTitle: TextView? = null
		var imageItem: ImageView? = null
		
	}
}
