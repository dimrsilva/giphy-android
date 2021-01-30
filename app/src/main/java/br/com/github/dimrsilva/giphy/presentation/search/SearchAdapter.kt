package br.com.github.dimrsilva.giphy.presentation.search

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.github.dimrsilva.giphy.R
import br.com.github.dimrsilva.giphy.application.model.Gif
import br.com.github.dimrsilva.giphy.databinding.SearchItemBinding
import com.danikula.videocache.HttpProxyCacheServer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class SearchAdapter(
    private val proxy: HttpProxyCacheServer,
    private val onToggleFavorite: (Int, Gif) -> Unit,
) : PagedListAdapter<Gif, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { gif ->
            val params = holder.binding.textureView.layoutParams as ConstraintLayout.LayoutParams
            params.dimensionRatio = "${gif.width}:${gif.height}"
            holder.binding.textureView.requestLayout()
            val mediaItem = MediaItem.fromUri(proxy.getProxyUrl(gif.mp4Url))
            holder.player.setMediaItem(mediaItem)
            holder.player.repeatMode = Player.REPEAT_MODE_ONE
            holder.player.prepare()
            holder.player.play()
            val drawableRes = if (gif.isFavorite) {
                R.drawable.ic_baseline_star_24
            } else {
                R.drawable.ic_baseline_star_outline_24
            }
            holder.binding.buttonFavorite.setImageDrawable(
                ContextCompat.getDrawable(holder.binding.buttonFavorite.context, drawableRes)
            )
            holder.binding.buttonFavorite.setOnClickListener {
                onToggleFavorite.invoke(position, gif)
            }
        }
    }

    class SearchViewHolder(
        val binding: SearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val player = SimpleExoPlayer.Builder(binding.root.context).build().also {
            it.setVideoTextureView(binding.textureView)
        }
    }

    class SearchDiffCallback : DiffUtil.ItemCallback<Gif>() {
        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
            return oldItem == newItem
        }
    }
}