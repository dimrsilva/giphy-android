package br.com.github.dimrsilva.giphy.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.github.dimrsilva.giphy.databinding.FragmentSearchBinding
import com.danikula.videocache.HttpProxyCacheServer
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel by viewModel<SearchViewModel>()
    private val cacheServer by inject<HttpProxyCacheServer>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        val adapter = SearchAdapter(cacheServer)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.pages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}