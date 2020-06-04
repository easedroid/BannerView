package com.bannerview.events

interface ItemCallback {
    fun onItemClicked(pos: Int)
    fun onChangeItem(pos: Int)
    fun stateChanged()
    fun onTouch(touched: Boolean)
}