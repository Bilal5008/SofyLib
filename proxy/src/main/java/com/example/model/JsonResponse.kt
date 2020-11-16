package com.example.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class JsonResponse {


    @SerializedName("LiveTestCaseID")
    @Expose
    private val liveTestCaseID: Int? = null

    @SerializedName("LiveTestCaseResultID")
    @Expose
    private val liveTestCaseResultID: Int? = null
}