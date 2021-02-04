package com.geekbrains.kotlin_lessons.sharedPeferences

import android.app.Activity
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPreferencesManager(activity: Activity) {
    private val sPreferences: SharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE)
    private var sEditor: Editor? = null
    private val editor: Editor
        get() = sPreferences.edit()

    fun storeBoolean(tag: String?, value: Boolean) {
        sEditor = editor
        sEditor!!.putBoolean(tag, value)
        sEditor!!.commit()
    }

    fun storeString(tag: String?, str: String?) {
        sEditor = editor
        sEditor!!.putString(tag, str)
        sEditor!!.commit()
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
        sEditor!!.putInt(tag, defValue)
        sEditor!!.commit()
    }

}