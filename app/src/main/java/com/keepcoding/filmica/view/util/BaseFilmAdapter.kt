package com.keepcoding.filmica.view.util

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.keepcoding.filmica.data.Film

open class BaseFilmAdapter<VH : BaseFilmHolder>(
    @LayoutRes val layoutItem: Int,
    val holderCreator: ((View) -> VH)
) : RecyclerView.Adapter<VH>() {

    protected val list: MutableList<Film> = mutableListOf()

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(recyclerView.context)
            .inflate(layoutItem, recyclerView, false)

        return holderCreator.invoke(itemView)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val film = list[position]
        viewHolder.bindFilm(film)
    }

    fun setFilms(films: MutableList<Film>) {
        list.clear()
        list.addAll(films)
        notifyDataSetChanged()
    }

    fun removeFilmAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getFilm(position: Int): Film {
        return list.get(position)
    }

    fun addFilm(film: Film) {
        list.add(film)
        notifyDataSetChanged()
    }

    fun updateFilms(films: MutableList<Film>) {
        films.forEach { film ->
            if(!list.contains(film)) {
                list.add(film)
            }
        }
        notifyItemRangeInserted(list.size, films.size)
    }
}