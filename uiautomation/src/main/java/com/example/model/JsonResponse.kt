package com.example.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class JsonResponse {


    @SerializedName("LiveTestCaseID")
    @Expose
    private val liveTestCaseID: Int? = 0

    @SerializedName("LiveTestCaseResultID")
    @Expose
    private val liveTestCaseResultID: Int? = 0
}