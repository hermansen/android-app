package se.swosch.jackson.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view).load(url).into(view)
}

@BindingAdapter("goneUnless")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: T) {
    if (recyclerView.adapter is BindableListAdapter<*>) {
        (recyclerView.adapter as BindableListAdapter<T>).setItems(items)
    }
}