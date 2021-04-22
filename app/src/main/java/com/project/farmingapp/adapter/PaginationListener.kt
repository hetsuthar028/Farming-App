package com.project.farmingapp.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


public abstract class PaginationListener : RecyclerView.OnScrollListener() {
    companion object{
        public val PAGE_START = 1
    }

    private var layoutManager: LinearLayoutManager? = null

    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */
    private val PAGE_SIZE = 10

    /**
     * Supporting only LinearLayoutManager for now.
     */
    open fun PaginationListener(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager!!.itemCount
        val firstVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}