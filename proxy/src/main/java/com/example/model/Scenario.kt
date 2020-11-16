package com.example.model

import com.google.gson.GsonBuilder
import org.json.simple.JSONObject
import java.util.ArrayList


class Scenario {


    var action: String? = null
    var actionValue: String? = null
    var activityName: String? = null
    var xml: String? = null
    var selectedComponent: SelectedComponent? = null
    var mouseEvents: ArrayList<MouseEvent>?= null
    var inVChecksumGroup: ArrayList<String>? = null
    var outVChecksumGroup: ArrayList<String>? = null
    var snapshotLocation: String? = null
    var snapshotLocation_before: String? = null
    var snapshotLocation_after: String? = null
    var androidPerformanceMonitors: AndroidPerformanceMonitors? = null
    var predictedActivityName: String? = null
    var actionIndex = 0
    var componentList: String? = null
    var performTime = 0

    constructor(
        action: String?,
        actionValue: String?,
        activityName: String?,
        xml: String?,
        selectedComponent: SelectedComponent?,
        mouseEvents: ArrayList<MouseEvent>?,
        inVChecksumGroup:  ArrayList<String>?,
        outVChecksumGroup:  ArrayList<String>?,
        snapshotLocation: String?,
        snapshotLocation_before: String?,
        snapshotLocation_after: String?,
        androidPerformanceMonitors: AndroidPerformanceMonitors?,
        predictedActivityName: String?,
        actionIndex: Int,
        componentList: String?,
        performTime: Int
    ) {
        this.action = action
        this.actionValue = actionValue
        this.activityName = activityName
        this.xml = xml
        this.selectedComponent = selectedComponent
        this.mouseEvents = mouseEvents
        this.inVChecksumGroup = inVChecksumGroup
        this.outVChecksumGroup = outVChecksumGroup
        this.snapshotLocation = snapshotLocation
        this.snapshotLocation_before = snapshotLocation_before
        this.snapshotLocation_after = snapshotLocation_after
        this.androidPerformanceMonitors = androidPerformanceMonitors
        this.predictedActivityName = predictedActivityName
        this.actionIndex = actionIndex
        this.componentList = componentList
        this.performTime = performTime
    }



    fun toJSON(): JSONObject? {
        val gson = GsonBuilder().serializeNulls().create()
        val jo = JSONObject()
        jo["action"] = action
        jo["actionValue"] = actionValue
        jo["activityName"] = activityName
        jo["xml"] = xml
        jo["selectedComponent"] = gson.toJsonTree(selectedComponent).asJsonObject
        jo["mouseEvents"] = gson.toJsonTree(mouseEvents).asJsonArray
        jo["inVChecksumGroup"] = inVChecksumGroup
        jo["outVChecksumGroup"] = outVChecksumGroup
        jo["snapshotLocation"] = snapshotLocation
        jo["snapshotLocation_before"] = snapshotLocation_before
        jo["snapshotLocation_after"] = snapshotLocation_after
        jo["androidPerformanceMonitors"] =androidPerformanceMonitors
        jo["predictedActivityName"] = predictedActivityName
        jo["actionIndex"] = actionIndex
        jo["componentList"] = componentList
        jo["performTime"] = performTime




        return jo
    }

}