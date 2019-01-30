package ai.kortnevdmitriy.msafiri.adapters

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.models.Item
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

/**
 * @author Saurabh Tomar
 */
class CustomGridViewAdapter(
	private val cont: Context,
	private val layoutResourceId: Int,
	private val data: ArrayList<Item>
) : ArrayAdapter<Item>(cont, layoutResourceId, data) {
	
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		var row = convertView
		var holder: RecordHolder? = null
		
		try {
			if (row == null) {
				val inflater = (context as Activity).layoutInflater
				row = inflater.inflate(layoutResourceId, parent, false)
				
				holder = RecordHolder()
				holder.txtTitle = row?.findViewById(R.id.item_text)
				holder.imageItem = row?.findViewById(R.id.item_image)
				row.tag = holder
			} else {
				holder = row.tag as RecordHolder
			}
			
			val item = data[position]
			holder.txtTitle?.text = item.title
			holder.imageItem?.setImageBitmap(item.image)
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