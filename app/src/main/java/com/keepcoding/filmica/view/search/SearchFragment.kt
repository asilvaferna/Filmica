package com.keepcoding.filmica.view.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.keepcoding.filmica.R
import com.keepcoding.filmica.data.Film
import com.keepcoding.filmica.data.FilmsRepo
import com.keepcoding.filmica.view.films.FilmsAdapter
import com.keepcoding.filmica.view.films.FilmsFragment
import com.keepcoding.filmica.view.util.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.layout_error.*

class SearchFragment: Fragment() {

    lateinit var listener: OnItemClickListener

    val list: RecyclerView by lazy {
        val instance = view!!.findViewById<RecyclerView>(R.id.list_films)
        instance.addItemDecoration(ItemOffsetDecoration(R.dimen.offset_grid))
        instance.setHasFixedSize(true)
        instance
    }

    val adapter: FilmsAdapter by lazy {
        val instance = FilmsAdapter { film ->
            this.listener.onItemClicked(film)
        }

        instance
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnItemClickListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = adapter

        search_text.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val query = editable.toString()

                if (query.count() > 2) {
                    requestFilms(query)
                }
            }
        })
    }

    fun requestFilms(query: String) {
        FilmsRepo.requestSearchFilms(query,
            { films ->
                progress?.visibility = View.INVISIBLE
                layoutError?.visibility = View.INVISIBLE
                list.visibility = View.VISIBLE
                adapter.setFilms(films)
            },
            { error ->
                progress?.visibility = View.INVISIBLE
                list.visibility = View.INVISIBLE
                layoutError?.visibility = View.VISIBLE

                error.printStackTrace()
            }, context!!)
    }

    interface OnItemClickListener {
        fun onItemClicked(film: Film)
    }
}