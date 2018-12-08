package com.keepcoding.filmica.view.watchlist


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_watchlist.*

import com.keepcoding.filmica.R
import com.keepcoding.filmica.data.FilmsRepo
import com.keepcoding.filmica.view.util.SwipeToDeleteCallback

class WatchlistFragment : Fragment() {

    val adapter: WatchlistAdapter by lazy {
        val instance = WatchlistAdapter()
        instance
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeHandler()
        watchlist.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

        loadWatchlist()
    }

    fun loadWatchlist() {
        FilmsRepo.watchlist(context!!) { films ->
            adapter.setFilms(films.toMutableList())
        }
    }

    private fun setupSwipeHandler() {
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {
                val position = holder.adapterPosition
                val temporaryFilm = adapter.getFilm(position)
                deleteFilmAt(position)

                Snackbar.make(watchlist, "Item removed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        FilmsRepo.saveFilm(context!!, temporaryFilm) {
                            adapter.addFilm(temporaryFilm)
                        }
                    }.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(watchlist)
    }

    private fun deleteFilmAt(position: Int) {
        val film = adapter.getFilm(position)
        FilmsRepo.deleteFilm(context!!, film) {
            adapter.removeFilmAt(position)
        }
    }
}
