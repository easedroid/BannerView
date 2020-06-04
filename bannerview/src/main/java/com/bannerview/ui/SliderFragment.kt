package com.bannerview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import coil.Coil
import coil.api.load
import coil.request.GetRequest.Companion.Builder

import com.bannerview.R
import com.bannerview.events.ItemCallback
import kotlinx.android.synthetic.main.fragment_slider.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SliderFragment : Fragment() {
    var callback: ItemCallback? = null

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
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainImage = view.findViewById<ImageView>(R.id.mainImage)
        var loading = view.findViewById<ProgressBar>(R.id.loading)
        var main = view.findViewById<FrameLayout>(R.id.main)
        loading.visibility = View.VISIBLE

        arguments.let {
            try {
                if (!it!!.isEmpty && it.containsKey("url")){
                    val imageLoader = Coil.imageLoader(activity!!)
                    val request = Builder(activity!!)
                        .data(it.getString("url"))
                        .error(R.drawable.sample)
                        .build()
                    GlobalScope.launch(Dispatchers.IO) {
                        val drawable = imageLoader.execute(request).drawable
                        GlobalScope.launch(Dispatchers.Main) {
                            loading.visibility = View.GONE
                            mainImage.setImageDrawable(drawable)
                        }
                    }

                }
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }

        main.setOnClickListener {
            arguments.let {
                if (it!=null && it.containsKey("pos")){
                    callback?.onItemClicked(it.getInt("pos"))
                }
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(url: String, position: Int, callback: ItemCallback?) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putString("url", url)
                    putInt("pos", position)
                }
                this.callback = callback
            }
    }
}
