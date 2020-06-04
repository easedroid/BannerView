package com.bannerview.events

interface IndicatorCallback {
    fun onTouch(touched: Boolean)
    fun onChangeItem(pos: Int)
}