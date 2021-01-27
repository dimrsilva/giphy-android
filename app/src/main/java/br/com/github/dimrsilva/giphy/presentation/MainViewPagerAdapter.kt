package br.com.github.dimrsilva.giphy.presentation

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.github.dimrsilva.giphy.R
import br.com.github.dimrsilva.giphy.presentation.search.SearchFragment
import br.com.github.dimrsilva.giphy.presentation.favorites.FavoritesFragment

class MainViewPagerAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = FRAGMENT_DEFINITIONS.size

    override fun createFragment(position: Int) = FRAGMENT_DEFINITIONS[position].first.invoke()

    fun tabName(position: Int): CharSequence {
        return activity.getString(FRAGMENT_DEFINITIONS[position].second)
    }

    companion object {
        private val FRAGMENT_DEFINITIONS = listOf(
            { SearchFragment() } to R.string.tab_search,
            { FavoritesFragment() } to R.string.tab_favorites,
        )
    }
}