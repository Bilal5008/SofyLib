package com.example.model

import com.google.gson.JsonObject
import org.json.JSONObject

class FinalObject {

    constructor(
            convertedObject: JsonObject?,
            generatedBySDK: Boolean?,

            )
    {
        this.convertedObject = convertedObject
        this.generatedBySDK = generatedBySDK
    }


    var convertedObject: JsonObject? = null
    var generatedBySDK: Boolean? = null
}