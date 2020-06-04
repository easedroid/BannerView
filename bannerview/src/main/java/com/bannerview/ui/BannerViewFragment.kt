package com.bannerview.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bannerview.R
import com.bannerview.adapter.IndicatorAdapter
import com.bannerview.events.BannerCallback
import com.bannerview.events.ItemCallback


class BannerViewFragment : Fragment() {
    private var callback: BannerCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerIndicator = view.findViewById<RecyclerView>(R.id.recyclerIndicator)
        recyclerIndicator.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerIndicator.setHasFixedSize(true)

        var list: MutableList<String> = arrayListOf()
        list.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
        list.add("https://homepages.cae.wisc.edu/~ece533/images/arctichare.png")
        list.add("https://homepages.cae.wisc.edu/~ece533/images/peppers.png")

        val adapter = IndicatorAdapter()
        recyclerIndicator.adapter = adapter
        adapter.setData(list)

        val builder = BannerPagerView.Builder()
            .with(context!!)
            .setView(view.findViewById(R.id.bannerView))
            .setSupportManager(childFragmentManager)
            .setDataItems(list)
            .setCallback(object : ItemCallback {
                override fun onItemClicked(pos: Int) {
                    callback?.onSelectBanner(pos)
                }

                override fun onChangeItem(pos: Int) {
                    adapter.setCurrentPosition(pos)
                    recyclerIndicator.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        recyclerIndicator.visibility = View.INVISIBLE
                    }, 2000)
                }

                override fun stateChanged() {

                }

                override fun onTouch(touched: Boolean) {
                    if (touched) {
                        recyclerIndicator.visibility = View.VISIBLE
                        /*Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            recyclerIndicator.visibility = View.INVISIBLE
                        }, 2000)*/
                    }else {
                        recyclerIndicator.visibility = View.INVISIBLE
                    }
                }
            })
            .build()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as BannerCallback
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BannerViewFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
