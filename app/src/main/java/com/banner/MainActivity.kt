package com.banner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bannerview.events.BannerCallback
import com.bannerview.events.ItemCallback
import com.bannerview.ui.BannerPagerView
import com.bannerview.ui.SliderView

class MainActivity : AppCompatActivity(), BannerCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list: MutableList<String> = arrayListOf()
        list.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
        list.add("https://homepages.cae.wisc.edu/~ece533/images/arctichare.png")
        list.add("https://homepages.cae.wisc.edu/~ece533/images/peppers.png")


        val builder = BannerPagerView.Builder()
            .with(this)
            //.setView(bannerView)
            .setSupportManager(supportFragmentManager)
            .setDataItems(list)
            .setCallback(object :ItemCallback{
                override fun onItemClicked(pos: Int) {
                    Log.e("Pos", pos.toString())
                }

                override fun onChangeItem(pos: Int) {

                }

                override fun stateChanged() {

                }

                override fun onTouch(touched: Boolean) {

                }
            })
            //.build()

        val slider = findViewById<SliderView>(R.id.slider)

        slider.setUpSliderView(builder)

        /*bannerView.setUpBannerView(supportFragmentManager, list)
        bannerView.setItemCallback(object :ItemCallback{
            override fun onItemClicked(pos: Int) {
                Log.e("Pos", pos.toString())
            }
        })*/

        /*val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.container, TestingFragment.newInstance())
        ft.commitNow()*/

    }

    override fun onSelectBanner(pos: Int) {
        Log.e("Pos", pos.toString())
    }
}
