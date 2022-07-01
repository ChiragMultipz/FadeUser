package com.eclipsa.fade.ui.add_review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.eclipsa.fade.R
import com.eclipsa.fade.databinding.ItemImageBinding
import com.eclipsa.fade.databinding.ItemServicesBinding
import com.eclipsa.fade.extension.loadingImage
import com.eclipsa.fade.ui.select_service.ResultItem
import com.eclipsa.fade.ui.select_service.ServiceAdapter
import com.eclipsa.fade.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ImageAdapter (private val context: Context, private val arrayListImage : ArrayList<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder (val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        loadingImage(context, arrayListImage[position], holder.binding.ivImage, false)
        holder.binding.cardItem.setOnClickListener {
            openImageBottomSheet(arrayListImage[position], context)
        }
    }

    private fun openImageBottomSheet(filename: String, context: Context) {
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(R.layout.custom_image_dialog)
        val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        val ivImage = dialog.findViewById<ImageView>(R.id.ivImage)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        loadingImage(context, filename, ivImage!!, false)

        ivClose!!.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun getItemCount(): Int = arrayListImage.size
}