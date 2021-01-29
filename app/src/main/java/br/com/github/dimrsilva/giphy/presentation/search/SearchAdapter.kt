package br.com.github.dimrsilva.giphy.presentation.search

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.databinding.SearchItemBinding
import com.danikula.videocache.HttpProxyCacheServer

class SearchAdapter(
    private val proxy: HttpProxyCacheServer
) : PagedListAdapter<Gif, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let {
            val params = holder.binding.videoView.layoutParams as ConstraintLayout.LayoutParams
            params.dimensionRatio = "${it.width}:${it.height}"
            holder.binding.videoView.requestLayout()
            val video = Uri.parse(proxy.getProxyUrl(it.mp4Url))
            holder.binding.videoView.setVideoURI(video)
            holder.binding.videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
                holder.binding.videoView.start()
            }
        }
    }

    class SearchViewHolder(
        val binding: SearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class SearchDiffCallback : DiffUtil.ItemCallback<Gif>() {
        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem == newItem
        }
    }
}