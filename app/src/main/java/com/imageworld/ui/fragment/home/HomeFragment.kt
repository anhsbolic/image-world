package com.imageworld.ui.fragment.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imageworld.R
import com.imageworld.model.Post
import com.imageworld.ui.adapter.PostListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomePresenter
    private var postList: MutableList<Post> = ArrayList()
    private lateinit var adapterRvPost: RecyclerView.Adapter<*>
    private lateinit var layoutManagerRvPost: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Init Presenter
        presenter = HomePresenter(this@HomeFragment)

        //Init RecyclerView
        initRecyclerView()

        //Load Post Data List
        presenter.loadPostListData()

    }

    override fun initRecyclerView() {
        adapterRvPost = PostListAdapter(postList)
        layoutManagerRvPost = LinearLayoutManager(activity)
        homeRv.adapter = adapterRvPost
        homeRv.layoutManager = layoutManagerRvPost
        homeRv.setHasFixedSize(false)
    }

    override fun showPostList(postList: MutableList<Post>) {
        if (homeRv != null) {
            for (i in 0 until postList.size) {
                this.postList.add(postList[i])
                val lastIndex = this.postList.lastIndex
                adapterRvPost.notifyItemInserted(lastIndex)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                HomeFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}
