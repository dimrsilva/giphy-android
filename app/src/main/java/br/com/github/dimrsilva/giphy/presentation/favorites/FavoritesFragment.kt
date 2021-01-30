package br.com.github.dimrsilva.giphy.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.github.dimrsilva.giphy.databinding.FragmentFavoritesBinding
import br.com.github.dimrsilva.giphy.presentation.list.GifListAdapter
import com.danikula.videocache.HttpProxyCacheServer
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()
    private val cacheServer by inject<HttpProxyCacheServer>()

    private var binding: FragmentFavoritesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        this.binding = binding

        val adapter = GifListAdapter(cacheServer) { position, gif ->
            viewModel.viewModelScope.launch {
                viewModel.removeFavorite(gif)
                invalidateData()
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.pages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        invalidateData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun invalidateData() {
        val adapter = binding?.recyclerView?.adapter as? GifListAdapter
        adapter?.currentList?.dataSource?.invalidate()
    }
}