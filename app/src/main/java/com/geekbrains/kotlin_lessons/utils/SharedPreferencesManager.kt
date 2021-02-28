package com.geekbrains.kotlin_lessons.utils

import android.app.Activity
import android.content.SharedPreferences

class SharedPreferencesManager(activity: Activity) {

    private val sPreferences: SharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE)

     var sEditor: SharedPreferences.Editor? = null
     val editor: SharedPreferences.Editor
        get() = sPreferences.edit()

    fun storeBoolean(tag: String?, value: Boolean) {
        sEditor = editor
        sEditor?.putBoolean(tag, value)
        sEditor?.commit()
    }

    fun storeString(tag: String?, str: String?) {
        sEditor = editor
        sEditor?.putString(tag, str)
        sEditor?.commit()
    }

    fun retrieveBoolean(tag: String?, defValue: Boolean): Boolean {
        return sPreferences.getBoolean(tag, defValue)
    }

    fun retrieveString(tag: String?, defStr: String?): String? {
        return sPreferences.getString(tag, defStr)
    }

    fun retrieveInt(tag: String?, defValue: Int): Int {
        return sPreferences.getInt(tag, defValue)
    }

    fun storeInt(tag: String?, defValue: Int) {
        sEditor = editor
        sEditor?.putInt(tag, defValue)
        sEditor?.commit()
    }

}