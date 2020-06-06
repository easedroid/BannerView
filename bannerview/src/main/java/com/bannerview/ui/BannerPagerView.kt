package com.bannerview.ui

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bannerview.config.Animate
import com.bannerview.config.CircularViewPagerHandler
import com.bannerview.events.IndicatorCallback
import com.bannerview.events.ItemCallback
import com.bannerview.events.ItemClickListeners
import java.util.*

class BannerPagerView constructor(
    context: Context,
    attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    var NUM_PAGES: Int = 0
    var currentPage: Int = 0
    var INTERVAL: Long = 5000
    var imageList: List<String> = arrayListOf()
    var callback: ItemCallback? = null
    var indicatorCallback: IndicatorCallback? = null
    var itemClickListeners: ItemClickListeners? = null
    var instance: BannerPagerView? = null

    fun setUpBannerView(manager: FragmentManager, imageList: List<String>){
        this.imageList = imageList
        this.NUM_PAGES = this.imageList.size
        this.adapter = ScreenSlidePagerAdapter(manager)
    }

    fun setItemCallback(listener: ItemCallback){
        callback = listener
    }

    fun setOnItemClickedListeners(itemClickListeners: ItemClickListeners) {
        this.itemClickListeners = itemClickListeners
    }

    fun setIndicatorsCallback(mIndicatorCallback: IndicatorCallback){
        indicatorCallback = mIndicatorCallback
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment =
            SliderFragment.newInstance(
                imageList[position],
                position,
                callback
            )
    }

    data class Builder constructor(var manager: FragmentManager? = null) {
        var mg: FragmentManager? = null
        var context: Context? = null
        var itemCallback: ItemCallback? = null
        var indicatorCallback: IndicatorCallback? = null
        var itemClickListeners: ItemClickListeners? = null
        var items: MutableList<String> = arrayListOf()
        var bannerView: BannerPagerView? = null
        var type: Animate? = null
        var interval: Long = 5000

        fun with(context: Context) = apply {
            this.context = context
        }

        fun setView(bannerView: BannerPagerView) = apply {
            this.bannerView = bannerView
        }

        fun setSupportManager(manager: FragmentManager) = apply {
            mg = manager
        }

        fun setCallback(callback: ItemCallback) = apply {
            itemCallback = callback
        }

        fun setDataItems(items: MutableList<String>) = apply {
            this.items = items
        }

        fun setAnimationType(type: Animate) = apply {
            this.type = type
        }

        fun setSlideInterval(long: Long) = apply {
            this.interval = long
        }

        fun setOnItemClickedListeners(itemClickListeners: ItemClickListeners) = apply {
            this.itemClickListeners = itemClickListeners
        }

        fun setIndicatorCallback(indicatorCallback: IndicatorCallback) = apply {
            this.indicatorCallback = indicatorCallback
        }

        fun build() : BannerPagerView {
            if (type == null){
                type = Animate.DepthPageTransformer
            }

            return if (bannerView!=null) {
                bannerView?.apply {
                    callback = itemCallback
                    this@Builder.indicatorCallback?.let { setIndicatorsCallback(it) }
                    itemClickListeners?.let { setOnItemClickedListeners(it) }
                    setUpBannerView(mg!!, items)
                    instance = bannerView
                    INTERVAL = interval
                    addOnPageChangeListener(CircularViewPagerHandler(
                        bannerView!!,
                        type!!,
                        callback,
                        indicatorCallback
                    ))
                }

                bannerView!!
            }else {
                val banner = BannerPagerView(context!!)
                banner.apply {
                    setItemCallback(itemCallback!!)
                    this@Builder.indicatorCallback?.let { setIndicatorsCallback(it) }
                    itemClickListeners?.let { setOnItemClickedListeners(it) }
                    setUpBannerView(mg!!, items)
                    instance = bannerView
                    INTERVAL = interval
                    addOnPageChangeListener(CircularViewPagerHandler(banner, type!!, callback, indicatorCallback))
                }

                banner
            }
        }

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        callback?.onTouch(true)
        indicatorCallback?.onTouch(true)
        return super.dispatchTouchEvent(ev)
    }

    val swipeHandler = Handler();
    val swipeRunnable = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0;
        }
        instance?.setCurrentItem(currentPage++, true);
    }

    val timer = Timer().schedule(
        object :TimerTask(){
            override fun run() {
                Log.e("Swiping", ">>>>>>")
                swipeHandler.post(swipeRunnable)
            }
        }, 2000, INTERVAL
    )


}