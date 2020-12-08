package com.example.model

import com.google.gson.GsonBuilder
import org.json.simple.JSONObject

class MouseEvent {
    var time: Long? = 0
    var point_x: Int? = 0

    var point_y: Int? = 0
    var mouseEventType: String? = "0"

    constructor(time: Long?, point_x: Int?, point_y: Int?, mouseEventType: String?) {
        this.time = time
        this.point_y = point_y
        this.point_x = point_x
        this.mouseEventType = mouseEventType
    }


    fun toJSON(): JSONObject? {
        val gson = GsonBuilder().serializeNulls().create()
        val jo = JSONObject()
        jo["time"] = time?.times(100)
        jo["point_y"] = point_y
        jo["point_x"] = point_x
        jo["mouseEventType"] = mouseEventType




        return jo
    }

}