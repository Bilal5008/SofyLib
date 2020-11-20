package com.example.model

import com.google.gson.GsonBuilder
import org.json.simple.JSONObject

class MouseEvent {
    var time: Long? = 0
    var pointX: Int? = 0

    var pointY: Int? = 0
    var mouseEventType: String? = "0"
    constructor(time: Long?, pointX: Int?, pointY: Int?, mouseEventType: String?) {
        this.time = time
        this.pointX = pointX
        this.pointY = pointY
        this.mouseEventType = mouseEventType
    }




    fun toJSON(): JSONObject? {
        val gson = GsonBuilder().serializeNulls().create()
        val jo = JSONObject()
        jo["time"] = time
        jo["point_x"] = pointX
        jo["point_y"] = pointY
        jo["mouseEventType"] = mouseEventType




        return jo
    }

}