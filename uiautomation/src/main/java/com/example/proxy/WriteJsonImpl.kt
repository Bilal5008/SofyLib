package com.example.proxy

import android.app.Activity
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class WriteJsonImpl {

    val READ_BLOCK_SIZE = 10000
    val FILE_NAME: String = "File-name"
    var fileReader: FileReader? = null
    var fileWriter: FileWriter? = null
    var bufferedReader: BufferedReader? = null
    var bufferedWriter: BufferedWriter? = null
    var response: String? = "this is file contentabc"
    var fileDir: File? = null

    private val TAG: String = "AppDebug"
    private lateinit var job: CompletableJob

    private var myActivity: Activity? = null

    fun writeJson(json: org.json.simple.JSONObject, p0: Activity) {
        myActivity = p0
        fileDir = File(p0.filesDir, FILE_NAME)
        if (!::job.isInitialized) {
            initjob()
        }
        startJobOrCancel(job, json)
    }

    private fun startJobOrCancel(job: CompletableJob, json: org.json.simple.JSONObject) {

        CoroutineScope(Dispatchers.IO + job).launch {
            Log.d(TAG, "coroutine ${this} is activated with job ${job}.")
            updateJobCompleteTextView(json)
        }

    }

    private fun updateJobCompleteTextView(json: org.json.simple.JSONObject) {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "coroutine ${this} is finised with job ${job}.")
            writeFileAsync(json)

        }
    }

    fun writeFileAsync(json: org.json.simple.JSONObject) {
        try {
            fileWriter = FileWriter(fileDir, true)
            bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter!!.write(json.toString() + "\n")
            Log.d(TAG, "File Saved at  ${fileDir}.")
            Toast.makeText(myActivity?.baseContext, "File Saved at " + fileDir, Toast.LENGTH_SHORT)
                .show()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bufferedWriter?.close()
                fileWriter?.close();
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
//        try {
//            var fileInputStream = myActivity?.openFileInput(FILE_NAME)
//            val inputBuffer = CharArray(READ_BLOCK_SIZE)
//            val InputRead = InputStreamReader(fileInputStream)
//            var s: String? = ""
//            var charRead: Int = 0
//            while (InputRead.read(inputBuffer).also { charRead = it } > 0) {
//                // char to string conversion
//                val readstring = String(inputBuffer, 0, charRead)
//                s += readstring
//            }
//
//            InputRead.close()
//
//            Toast.makeText(myActivity?.baseContext, "Input Read  $s", Toast.LENGTH_LONG).show()
//
//
//        } catch (e: IOException) {
//            e.printStackTrace();
//        }

    }


    private fun initjob() {

        job = Job()
        job.invokeOnCompletion {
            it?.message.let {
                var msg = it
                if (msg.isNullOrBlank()) {
                    msg = "Unknown cancellation error."
                }
                Log.e(TAG, "${job} was cancelled. Reason: ${msg}")
//                showToast(msg)
            }
        }


    }

//    fun appendInKey(r: org.json.simple.JSONArray, p0: Activity) {
//
//        val NewObj = JSONObject()
//        NewObj.put("travelTime", "travelTime")
//        NewObj.put("travelTime", "travelTime")
//        r.add(NewObj);
//        WriteJsonImpl().writeJson(r, p0)
//
//
//    }
}

