package com.bannerview.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bannerview.R
import com.bannerview.adapter.IndicatorAdapter
import com.bannerview.events.IndicatorCallback


class SliderView constructor(
    context: Context,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs) {


    lateinit var bannerView: BannerPagerView
    lateinit var recyclerIndicator: RecyclerView
    var adapter: IndicatorAdapter? = null

    init {
        //LayoutInflater.from(context).inflate(R.layout.slider_view, this, true)
        /*val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.slider_view, this, true);*/
        View.inflate(context, R.layout.slider_view, this)
        bannerView = findViewById<BannerPagerView>(R.id.bannerView)
        recyclerIndicator = findViewById<RecyclerView>(R.id.recyclerIndicator)
        recyclerIndicator.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerIndicator.setHasFixedSize(true)

        adapter = IndicatorAdapter()
        recyclerIndicator.adapter = adapter
    }

    fun setUpSliderView(builder: BannerPagerView.Builder) {
        adapter!!.setData(builder.items.toMutableList())
        bannerView.let {

        }

        builder.setView(bannerView)
        builder.setIndicatorCallback(object :IndicatorCallback{
            override fun onTouch(touched: Boolean) {
                Log.e("Touch", ">>>>>>>>>>>>>>>")
                if (touched) {
                    recyclerIndicator.visibility = View.VISIBLE
                    /*Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        recyclerIndicator.visibility = View.INVISIBLE
                    }, 2000)*/
                }else {
                    recyclerIndicator.visibility = View.INVISIBLE
                }
            }

            override fun onChangeItem(pos: Int) {
                Log.e("Change", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                adapter?.let {
                    it.setCurrentPosition(pos)
                    recyclerIndicator.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        recyclerIndicator.visibility = View.INVISIBLE
                    }, 2000)
                }
            }
        })

        builder.build()
    }

}


