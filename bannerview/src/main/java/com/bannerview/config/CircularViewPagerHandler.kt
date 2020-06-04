package com.bannerview.config

import androidx.viewpager.widget.ViewPager
import com.bannerview.events.IndicatorCallback
import com.bannerview.events.ItemCallback

class CircularViewPagerHandler(
    viewPager: ViewPager,
    type: Animate,
    callback: ItemCallback?,
    indicatorCallback: IndicatorCallback?
): ViewPager.OnPageChangeListener {

    private val mViewPager: ViewPager? = viewPager
    private var mCurrentPosition = 0
    private var mScrollState = 0
    private var mCallback: ItemCallback? = callback
    private var mIndicatorCallback: IndicatorCallback? = indicatorCallback
    init {
        if (type == Animate.DepthPageTransformer){
            mViewPager?.setPageTransformer(true, DepthPageTransformer())
        }else {
            mViewPager?.setPageTransformer(true, ZoomOutPageTransformer())
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        handleScrollState(state);
        mScrollState = state;
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        mCurrentPosition = position;
        mCallback?.onChangeItem(position)
        mIndicatorCallback?.onChangeItem(position)

    }

    private fun handleScrollState(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE && mScrollState == ViewPager.SCROLL_STATE_DRAGGING) {
            setNextItemIfNeeded()
        }
    }

    private fun setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem()
        }
    }

    private fun isScrollStateSettling(): Boolean {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING
    }

    private fun handleSetNextItem() {
        val lastPosition = mViewPager!!.adapter!!.count - 1
        if (mCurrentPosition == 0) {
            mViewPager.setCurrentItem(lastPosition, true)
        } else if (mCurrentPosition == lastPosition) {
            mViewPager.setCurrentItem(0, true)
        }
    }

}