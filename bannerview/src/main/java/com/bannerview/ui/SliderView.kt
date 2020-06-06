package com.bannerview.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bannerview.R
import com.bannerview.adapter.IndicatorAdapter
import com.bannerview.config.Gravity
import com.bannerview.events.IndicatorCallback


class SliderView constructor(
    context: Context,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs) {


    var bannerView: BannerPagerView
    var recyclerIndicator: RecyclerView
    var adapter: IndicatorAdapter? = null
    var gravity: Gravity? = null
    var auto_hide_indicator: Boolean = true

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
        val ta = getContext().obtainStyledAttributes(attrs, R.styleable.SliderView)
        gravity = Gravity.values()[ta.getInt(R.styleable.SliderView_indicator_gravity,0)]

        if (gravity == Gravity.CENTER){
            val params = FrameLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            params.gravity = android.view.Gravity.BOTTOM or android.view.Gravity.CENTER
            params.bottomMargin = 15
            recyclerIndicator.layoutParams = params
            recyclerIndicator.background = ContextCompat.getDrawable(context, R.drawable.bg_oval_indicator)
        }

        val auto_hide = ta.getBoolean(0, true)

        if (!auto_hide){
            auto_hide_indicator = auto_hide
        }


        adapter = IndicatorAdapter()
        recyclerIndicator.adapter = adapter

        ta.recycle()
    }

    fun setIndicatorGravity(){

    }

    fun setUpSliderView(builder: BannerPagerView.Builder) {
        adapter!!.setData(builder.items.toMutableList())
        bannerView.let {

        }

        builder.setView(bannerView)
        builder.setIndicatorCallback(object :IndicatorCallback{
            override fun onTouch(touched: Boolean) {
                Log.e("Touch", ">>>>>>>>>>>>>>>")

                if (auto_hide_indicator) {
                    if (touched) {
                        recyclerIndicator.visibility = View.VISIBLE
                        /*Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        recyclerIndicator.visibility = View.INVISIBLE
                    }, 2000)*/
                    } else {
                        recyclerIndicator.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onChangeItem(pos: Int) {
                Log.e("Change", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                adapter?.let {
                    it.setCurrentPosition(pos)
                    if (auto_hide_indicator) {
                        recyclerIndicator.visibility = View.VISIBLE
                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            recyclerIndicator.visibility = View.INVISIBLE
                        }, 2000)
                    }
                }
            }
        })

        builder.build()
    }

}


