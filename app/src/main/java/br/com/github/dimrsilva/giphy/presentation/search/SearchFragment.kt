package br.com.github.dimrsilva.giphy.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.github.dimrsilva.giphy.databinding.FragmentSearchBinding
import br.com.github.dimrsilva.giphy.presentation.list.GifListAdapter
import com.google.android.exoplayer2.source.MediaSourceFactory
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel by viewModel<SearchViewModel>()
    private val mediaSourceFactory by inject<MediaSourceFactory>()

    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        this.binding = binding

        val adapter = GifListAdapter(mediaSourceFactory) { gif ->
            viewModel.toggleFavorite(gif)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.editTextSearch.doOnTextChanged { text, _, _, _ -> viewModel.searchTerm.value = text.toString() }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            binding.recyclerView.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }
        viewModel.pages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val adapter = binding?.recyclerView?.adapter as? GifListAdapter
        adapter?.currentList?.dataSource?.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}