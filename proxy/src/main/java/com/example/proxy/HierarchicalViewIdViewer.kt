package com.example.proxy

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import java.util.ArrayList

object HierarchicalViewIdViewer {
    var textViewList: ArrayList<AppCompatTextView>? = arrayListOf()
    var editTextList: ArrayList<AppCompatEditText>? = arrayListOf()
    var buttonList: ArrayList<AppCompatButton>? = arrayListOf()
    var imageButtonList: ArrayList<AppCompatImageButton>? = arrayListOf()
    var addingListenerBoolean: Boolean = true


    fun debugViewIds(view: View, logtag: String?, myWindowCallback: MyWindowCallback): View {
        Log.v(logtag, "traversing: " + view.javaClass.simpleName + ", id: " + view.id)
        return if (view.parent != null && view.parent is ViewGroup) {
            debugViewIds(view.parent as View, logtag, myWindowCallback)
        } else {
            debugChildViewIds(view, logtag, 0, myWindowCallback)
            return view
        }
    }

    private fun debugChildViewIds(view: View, logtag: String?, spaces: Int, myWindowCallback: MyWindowCallback) {
        if (view is ViewGroup) {
            val group = view
            for (i in 0 until group.childCount) {
                val child = group.getChildAt(i)
                addListing(child)
                Log.v(logtag, padString("view: " + child.javaClass.simpleName + "(" + child.id + ")", spaces))
                debugChildViewIds(child, logtag, spaces + 1, myWindowCallback)
            }
            Log.v(logtag, "this is calling")

            if (addingListenerBoolean) {
                addingListenerBoolean = false
                Log.v(logtag, "this should be call last")
                Log.v(logtag, "textViewList-----SIZE" + textViewList?.size)
                Log.v(logtag, "buttonList-----SIZE" + buttonList?.size)
                Log.v(logtag, "editTextList-----SIZE" + editTextList?.size)
                Log.v(logtag, "imageButtonList-----SIZE" + imageButtonList?.size)

//                addingListeners(textViewList, buttonList, editTextList, imageButtonList)
            }

        }

    }

    private fun addListing(finalView: View?): Boolean {
        if (finalView is AppCompatTextView) {
//            textViewList?.add(finalView as AppCompatTextView)
            // addTextViewListener(finalView as TextView, i, activity)
        }
        if (finalView is AppCompatButton) {
//            buttonList?.add(finalView as AppCompatButton)
            //addOnTouchListener(finalView as Button, i, activity)
        }
        if (finalView is AppCompatEditText) {
//            editTextList?.add(finalView as AppCompatEditText)

            //addOnTouchListenerForEditText(finalView as EditText, i, activity)
        }
        if (finalView is AppCompatImageButton) {
//            imageButtonList?.add(finalView as AppCompatImageButton)

            //addOnTouchListenerForEditText(finalView as EditText, i, activity)
        }

//        MyWindowCallback().addingListeners(textViewList, buttonList, editTextList, imageButtonList)
//        for (i in 0 until textViewList!!.size) {
//            MyWindowCallback.addTextViewListener(textViewList!![i] as TextView, i, activity)
//        }
//        for (i in 0 until buttonList!!.size) {
//            addOnTouchListener(buttonList!![i] as Button, i, activity)
//
//        }


        return true
    }

    private fun padString(str: String, noOfSpaces: Int): String {
        if (noOfSpaces <= 0) {
            return str
        }
        val builder = StringBuilder(str.length + noOfSpaces)
        for (i in 0 until noOfSpaces) {
            builder.append(' ')
        }
        return builder.append(str).toString()
    }
}