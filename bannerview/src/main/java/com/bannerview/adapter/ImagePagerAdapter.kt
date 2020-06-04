package com.bannerview.adapter

import android.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter


class ImagePagerAdapter: PagerAdapter() {
    private val mImages = intArrayOf(
        R.drawable.ic_lock_power_off,
        R.drawable.ic_lock_power_off,
        R.drawable.ic_lock_power_off,
        R.drawable.ic_lock_power_off
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ImageView
    }

    override fun getCount(): Int {
        return mImages.size;
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}