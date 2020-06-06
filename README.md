# BannerView

[![](https://jitpack.io/v/easedroid/BannerView.svg)](https://jitpack.io/#easedroid/BannerView)
[![](https://jitpack.io/v/easedroid/BannerView.svg)](https://jitpack.io/#easedroid/BannerView)

## This is a simple library for showing banners(images) with sliding features.



To use this library you need add these in your project level build.gradle file
    
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

And these lines in your app level build.gradle file
   
    dependencies {
            implementation 'com.github.easedroid:BannerView:CURRENT_VERSION'
    }
    
    
Sample usage of this library are shown below,

    -- Add this in your XML
    
    <com.bannerview.ui.SliderView
            android:id="@+id/slider"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            app:indicator_gravity="CENTER"
            app:auto_hide_indicator="true"/> 
        
    -- initiate this view in you activity like this,
    
        val slider = findViewById<SliderView>(R.id.slider)
         val builder = BannerPagerView.Builder()
                    .with(this)
                    .setSupportManager(supportFragmentManager)
                    .setDataItems(**list_of_image_urls**)
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
                    
        -- Finally set the builder instance to Banner view object like this,
        
        slider.setUpSliderView(builder)

That's it. It can be modified as your need. I will update other usage very soon. 

--#Stay Tuned
    