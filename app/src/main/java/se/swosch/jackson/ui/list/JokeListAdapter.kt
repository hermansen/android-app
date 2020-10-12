package se.swosch.jackson.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_joke_item.view.*
import se.swosch.jackson.R
import se.swosch.jackson.databinding.BindableListAdapter

class JokeListAdapter : RecyclerView.Adapter<JokeListAdapter.JokeViewHolder>(),
    BindableListAdapter<List<ListViewModel.ChuckWrapper>> {

    private var items = emptyList<ListViewModel.ChuckWrapper>()

    override fun setItems(items: List<ListViewModel.ChuckWrapper>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return JokeViewHolder(inflater.inflate(R.layout.layout_joke_item, parent, false))
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(jokeWrapper: ListViewModel.ChuckWrapper) {
            Glide.with(itemView).load(jokeWrapper.joke.imgUrl).into(itemView.icon)
            itemView.joke.text = jokeWrapper.joke.joke
            itemView.listItem.setOnClickListener { jokeWrapper.clickListener() }
        }
    }
}