package com.example.proxy

import android.app.Activity
import android.app.Application
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.utils.Utilities.Companion.isFirstTime
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStreamReader


open class ApplicationStart : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        val TAG = "Activity lifecycler"
    }

    val FILE_NAME: String = "File-name"
    val READ_BLOCK_SIZE = 1000000
    override fun onCreate() {
        super.onCreate()
        println("Notification onCreatetaeAxiaAplication")
//        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {


    }


    override fun onActivityStarted(p0: Activity) {
        println("Lifeycler onActivityStarted")
        val win: Window = p0.window
        val localCallback: Window.Callback = win.callback
        win.callback = MyWindowCallback(localCallback, p0)
        if (!isFirstTime!!) {
            isFirstTime = true

            (win.callback as MyWindowCallback).writeFileFirstTime()
            println("Lifeycler MyWindowCallback.writeFileFirstTime()")
        }


    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {
        println("Lifeycler onActivityStopped")
        println("Lifeycler ReadJsonOnMouseEvent(p0) ${ReadJsonOnMouseEvent(p0)}")

        val jsonObject = org.json.JSONObject()
        try {

            jsonObject.put("Content", ReadJsonOnMouseEvent(p0))
            Log.i(TAG, "package name" + packageName)
            jsonObject.put("LiveTestCaseName", packageName)
            jsonObject.put("TestResult", 1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }



        AndroidNetworking.post("http://api-sofy-test.azurewebsites.net/api/Applications/fe62aa10-2398-4f16-8b44-3d792338a848/CreateLiveTestCaseAndTestRun")
            .addJSONObjectBody(jsonObject) // posting json
            .setTag("test")
            .addHeaders("Content-Type", "application/json")
            .addHeaders("SofyAuth", "67935C5B-A009-4DC8-9EC6-2F8E9D58E6DC")

            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    // do anything with response
                    Log.i(TAG, "Success")
                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.i(TAG, "Error $error ")
                }
            })

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }


    private fun ReadJsonOnMouseEvent(activity: Activity): String {
//        println("Lifeycler ReadJsonOnMouseEvent start")
//        var convertedObject: JsonObject? = null
//        var s: String? = ""
//        try {
//            println("Lifeycler ReadJsonOnMouseEvent tryblock")
//            var fileInputStream = activity.openFileInput(FILE_NAME)
//            val inputBuffer = CharArray(READ_BLOCK_SIZE)
//            val InputRead = InputStreamReader(fileInputStream)
//
//            var charRead: Int = 0
//            while (InputRead.read(inputBuffer).also { charRead = it } > 0) {
//                // char to string conversion
//                val readstring = String(inputBuffer, 0, charRead)
//                s += readstring
//                convertedObject = Gson().fromJson(s, JsonObject::class.java)
//                println("Lifeycler ReadJsonOnMouseEvent while loop")
//            }
//            InputRead.close()
//
//        } catch (e: IOException) {
//            e.printStackTrace();
//        } finally {
//
//            println("Lifeycler ReadJsonOnMouseEvent ${convertedObject.toString()}")
//            return convertedObject.toString()
//        }


        var convertedObject: JsonObject? = null
        try {
            var fileInputStream = activity?.openFileInput(FILE_NAME)
            val inputBuffer = CharArray(READ_BLOCK_SIZE)
            val stream = InputStreamReader(fileInputStream)
            var s: String? = ""
            var charRead: Int = 0
            while (stream.read(inputBuffer).also { charRead = it } > 0) {
                // char to string conversion
                val readstring = String(inputBuffer, 0, charRead)
                s += readstring

            }
            convertedObject = Gson().fromJson(s, JsonObject::class.java)
            stream.close()

            println("Input Read json as string$s")
            println("Input convertedObject ${convertedObject.toString()}")
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {

            return convertedObject.toString()
        }
    }

}
