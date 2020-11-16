package com.example.proxy


import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Rect

import android.util.Log
import android.view.MotionEvent
import android.view.View

import android.view.ViewGroup
import androidx.core.view.get


class MyDispatcherTwo(context: Context, var activity: Activity) : ViewGroup(context) {
    private val TAG: String = MyDispatcherTwo::class.java.getSimpleName()
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var actionCode = ""
        Log.i(TAG, "onInterceptTouchEvent")
        val action = ev!!.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> Log.i(TAG, "onInterceptTouchEvent.ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.i(TAG, "onInterceptTouchEvent.ACTION_MOVE")
            MotionEvent.ACTION_UP -> actionCode = "RELEASE"
        }
        if (actionCode === "RELEASE") {


//            activity.resources.getLayout(id)
//            var id = activity.window?.decorView?.id
//            if (id != null) {
//                activity.setContentView(id)
//            }

//            val rootViewGroup = activity.window?.decorView?.rootView
//            rootViewGroup?.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
//                when (motionEvent.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        Log.i(TAG, "rootViewGroup viewcheck ${view.visibility}")
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        val rootGlobalRect = Rect()
//                        Log.i(TAG, "rootViewGroup viewcheck ${view.visibility}")
//                        Log.i(TAG, "rootViewGroup viewcheck ${view.getFocusedRect(rootGlobalRect)}")
//
//                    }
//                }
//                return@OnTouchListener true
//            })


//            val rootViewGroup1 = (activity.window?.decorView?.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
            val rootViewGroup1 =
                ((activity.window?.decorView?.findViewById<View>(R.id.content) as ViewGroup).getChildAt(
                    0
                ) as ViewGroup).get(4)
            rootViewGroup1.setOnTouchListener(object : OnTouchListener {
                override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                    Log.i(TAG, " rootViewGroupNEW viewcheck $view")



                    when (motionEvent?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Log.i(TAG, " rootViewGroup1 viewcheck ${view?.visibility}")
                        }
                        MotionEvent.ACTION_UP -> {
                            val rootGlobalRect = Rect()
                            Log.i(TAG, " rootViewGroup1 viewcheck ${view?.visibility}")
                            Log.i(
                                TAG,
                                " rootViewGroup1 viewcheck ${view?.getFocusedRect(rootGlobalRect)}"
                            )

                        }
                    }
                    return true
                }

            }


//            rootViewGroup1?.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
//
//                when (motionEvent.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        Log.i(TAG, " rootViewGroup1 viewcheck ${view.visibility}")
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        val rootGlobalRect = Rect()
//                        Log.i(TAG, " rootViewGroup1 viewcheck ${view.visibility}")
//                        Log.i(
//                            TAG,
//                            " rootViewGroup1 viewcheck ${view.getFocusedRect(rootGlobalRect)}"
//                        )
//
//                    }
//                }
//                return@OnTouchListener true
//            })

            )


        }

        return super.onInterceptTouchEvent(ev)
    }
}

