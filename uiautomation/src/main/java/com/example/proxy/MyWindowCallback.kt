package com.example.proxy

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.text.InputType
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.get
import com.an.deviceinfo.device.model.App
import com.an.deviceinfo.device.model.Device
import com.example.model.*
import com.example.utils.Utilities.Companion.factorForResolutionLenght
import com.example.utils.Utilities.Companion.factorForResolutionWidth
import com.example.utils.Utilities.Companion.formatSize
import com.example.utils.Utilities.Companion.gUID
import com.example.utils.Utilities.Companion.getRamForDevice
import com.example.utils.Utilities.Companion.getScreenResolution
import com.example.utils.Utilities.Companion.packageName
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.simple.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*


class MyWindowCallback() : Window.Callback {

    val FILE_NAME: String = "File-name"
    val READ_BLOCK_SIZE = 1000000
    var localCallback: Window.Callback? = null
    var activity: Activity? = null
    var mouseEventList: ArrayList<MouseEvent>? = null
    var mouseEventListFinal: ArrayList<MouseEvent>? = null
    var finalView: View? = null
    var bounds: String? = null
    var uId: Int? = 0
    var focused: Boolean? = false
    var visible: Boolean? = false
    var enabled: Boolean? = false
    var focusable: Boolean? = false
    var longClickable: Boolean? = false
    var scrollable: Boolean? = false
    var isClickable: Boolean? = false
    var viewText: String? = ""
    var app: App? = null
    var d: Device? = null
    var xPath: String? = ""
    var mclazz: String? = ""
    var mType: String? = ""
    var ContentDesc: String? = ""
    var ActionValue: String? = ""

    companion object {
        const val FOO = "MyWindowCallback"
        const val TextViewFoo = "TextView"
        const val EditTextFoo = "EditText"
    }

    constructor(localCallback: Window.Callback, activity: Activity) : this() {
        this.activity = activity
        this.localCallback = localCallback

//        var driver: WebDriver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
        mouseEventList = arrayListOf()
        var viewGroupSize =
                ((this.activity?.window?.decorView?.findViewById<View>(R.id.content) as? ViewGroup)?.getChildAt(
                        0
                ) as? ViewGroup)?.childCount

        if (this.activity != null && this.activity?.window?.decorView?.findViewById<View>(R.id.content) as? ViewGroup != null) {


            for (i in 0 until viewGroupSize!!) {
                finalView = ((this.activity?.window?.decorView?.findViewById<View>(R.id.content) as? ViewGroup)?.getChildAt(0) as? ViewGroup)?.get(i)
                if (finalView is Button) {
                    addOnTouchListener(finalView as Button, i, activity)
                } else if (finalView is EditText) {
                    addSetTextListener(finalView as EditText, i, activity)
                } else if (finalView is TextView) {
                    addTextViewListener(finalView as TextView, i, activity)
                }
                Log.i(EditTextFoo, "UID $i")

            }


        }
    }


    private fun addTextViewListener(finalView: TextView, i: Int, activity: Activity) {
        finalView?.setOnTouchListener { view, motionEvent ->
            Log.i(TextViewFoo, "FirstAddedTOuchListener $view")

            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i(TextViewFoo, " rootViewGroup1 viewcheck ${view?.visibility}")
                }
                MotionEvent.ACTION_UP -> {
                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    val location = IntArray(2)
                    Log.i(TextViewFoo, " rootViewGroup1 viewcheck ${view?.visibility}")
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 viewcheck ${
                                view?.getLocationOnScreen(location).toString()
                            }"
                    )
                    Log.i(TextViewFoo, " rootViewGroup1 UID ${i}")

                    uId = i
                    val rectf = Rect()

//For coordinates location relative to the parent

//For coordinates location relative to the parent
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 getLocalVisibleRect ${view.getLocalVisibleRect(rectf)}"
                    )
//For coordinates location relative to the screen/display

//For coordinates location relative to the screen/display

                    Log.i(
                            FOO,
                            " rootViewGroup1 getGlobalVisibleRect ${view.getGlobalVisibleRect(rectf)}"
                    )
                    Log.i("left         :", rectf.left.toString());
                    Log.i("right        :", rectf.right.toString());
                    Log.i("top          :", rectf.top.toString());
                    Log.i("bottom       :", rectf.bottom.toString());

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 left ${view.left}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 Top ${view.top}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 right ${view.right}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 bottom ${view.bottom}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 focus ${view.hasFocus()}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 visibility ${view.visibility}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 enable ${view.isEnabled}"
                    )


                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 focusable ${view.isFocusable}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 longclicked ${view.isLongClickable}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 clickable ${view.isClickable}"
                    )

                    var firstCordinates = arrayOf(view.left, view.top)


                    var secondCordinates = arrayOf(view.right, view.bottom)

                    bounds =
                            "${Arrays.toString(firstCordinates)} ${Arrays.toString(secondCordinates)}"

                    focused = view.isFocused
                    visible = view.visibility == 0
                    enabled = view.isEnabled
                    focusable = view.isFocusable
                    longClickable = view.isLongClickable
                    if (view.isScrollContainer) {
                        scrollable = true
                    }
                    isClickable = view.isClickable

                    view.findViewById<TextView>(view.id)
                    viewText = (view as TextView).text as String?
                    xPath = "//hierarchy[1]/"
                    mclazz = "TextField"
                    mType = "XCUIElementTypeEditText"
                    ContentDesc = viewText
                    ActionValue = ""

                }
            }
            true
        }

    }

    private fun addSetTextListener(finalView: EditText, i: Int, activity: Activity) {


        finalView.imeOptions = EditorInfo.IME_ACTION_DONE

        finalView.setOnEditorActionListener(OnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (finalView.length() > 0) {
                    Log.i(EditTextFoo, "View ${finalView.text}")
                    Log.i(EditTextFoo, "UID $i")


                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    val location = IntArray(2)
                    Log.i(TextViewFoo, " rootViewGroup1 viewcheck ${view?.visibility}")
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 viewcheck ${
                                view?.getLocationOnScreen(location).toString()
                            }"
                    )
                    Log.i(TextViewFoo, " rootViewGroup1 UID ${i}")

                    uId = i
                    val rectf = Rect()

//For coordinates location relative to the parent

//For coordinates location relative to the parent
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 getLocalVisibleRect ${view.getLocalVisibleRect(rectf)}"
                    )
//For coordinates location relative to the screen/display

//For coordinates location relative to the screen/display

                    Log.i(
                            FOO,
                            " rootViewGroup1 getGlobalVisibleRect ${view.getGlobalVisibleRect(rectf)}"
                    )
                    Log.i("left         :", rectf.left.toString());
                    Log.i("right        :", rectf.right.toString());
                    Log.i("top          :", rectf.top.toString());
                    Log.i("bottom       :", rectf.bottom.toString());

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 left ${view.left}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 Top ${view.top}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 right ${view.right}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 bottom ${view.bottom}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 focus ${view.hasFocus()}"
                    )
                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 visibility ${view.visibility}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 enable ${view.isEnabled}"
                    )


                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 focusable ${view.isFocusable}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 longclicked ${view.isLongClickable}"
                    )

                    Log.i(
                            TextViewFoo,
                            " rootViewGroup1 clickable ${view.isClickable}"
                    )

                    var firstCordinates = arrayOf(view.left, view.top)


                    var secondCordinates = arrayOf(view.right, view.bottom)

                    bounds =
                            "${Arrays.toString(firstCordinates)} ${Arrays.toString(secondCordinates)}"

                    focused = view.isFocused
                    visible = view.visibility == 0
                    enabled = view.isEnabled
                    focusable = view.isFocusable
                    longClickable = view.isLongClickable
                    if (view.isScrollContainer) {
                        scrollable = true
                    }
                    isClickable = view.isClickable

                    view.findViewById<EditText>(view.id)
                    viewText = (view as EditText).text.toString()
                    xPath = "//hierarchy[1]/"
                    mclazz = "dummy"
                    mType = "XCUIElementTypeTextField"
                    ContentDesc = "dummy"
                    ActionValue = viewText

//                    if (view.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
//                        mclazz = "SecureTextField"
//                        mType = "XCUIElementTypeSecureTextField"
//                    }


                    // settext ---- > dummy
                    // click on edittext -- >EditText


                    mouseEventList?.add(
                            MouseEvent(null)
                    )
                    app = App(activity)
                    var selectedComponent = SelectedComponent(
                            `package` = app?.packageName,
                            bounds = bounds.toString(),
                            uId = uId,
                            focused = focused,
                            focusable = focusable,
                            clickable = isClickable,
                            enabled = enabled,
                            longClickable = longClickable,
                            scrollable = scrollable,
                            visible = visible,
                            resourceId = "-1",
                            xpath = xPath,
                            text = viewText,
                            clazz = mclazz,
                            Type = ContentDesc,
                            ContentDesc = ContentDesc,


                            )

                    var device = DeviceConfigured(

                            true,
                            "",
                            d!!.manufacturer,
                            getScreenResolution(this.activity),
                            formatSize(getRamForDevice(this.activity)),
                            "DEFAULT",
                            d!!.manufacturer,
                            d!!.model,
                            d!!.board,
                            System.getProperty("os.arch"),
                            d!!.osVersion,
                            Build.VERSION.SDK_INT.toString(),
                            d!!.device,
                            "ANDROID"
                    )
                    var appInfo = AppInfo(
                            app?.packageName,
                            app?.appName,
                            app?.appVersionCode.toString(),
                            Build.VERSION.SDK_INT.toString(),
                            null,
                            null,
                            "appIconFile",
                            "appFile"
                    )

                    var action = "SET_TEXT"
                    get64EncodedString(
                            writeJSONObjectListener,
                            selectedComponent,
                            action,
                            device,
                            appInfo

                    )


                }

            }
            false
        })


    }

    private fun addOnTouchListener(finalView: Button, i: Int, activity: Activity) {
        finalView?.setOnTouchListener { view, motionEvent ->
            Log.i(FOO, "FirstAddedTOuchListener $view")

            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i(FOO, " rootViewGroup1 viewcheck ${view?.visibility}")
                }
                MotionEvent.ACTION_UP -> {
                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    val location = IntArray(2)
                    Log.i(FOO, " rootViewGroup1 viewcheck ${view?.visibility}")
                    Log.i(
                            FOO,
                            " rootViewGroup1 viewcheck ${
                                view?.getLocationOnScreen(location).toString()
                            }"
                    )
                    Log.i(FOO, " rootViewGroup1 UID ${i}")

                    uId = i
                    val rectf = Rect()

//For coordinates location relative to the parent

//For coordinates location relative to the parent
                    Log.i(
                            FOO,
                            " rootViewGroup1 getLocalVisibleRect ${view.getLocalVisibleRect(rectf)}"
                    )
//For coordinates location relative to the screen/display

//For coordinates location relative to the screen/display

                    Log.i(
                            FOO,
                            " rootViewGroup1 getGlobalVisibleRect ${view.getGlobalVisibleRect(rectf)}"
                    )
                    Log.i("left         :", rectf.left.toString());
                    Log.i("right        :", rectf.right.toString());
                    Log.i("top          :", rectf.top.toString());
                    Log.i("bottom       :", rectf.bottom.toString());

                    Log.i(
                            FOO,
                            " rootViewGroup1 left ${view.left}"
                    )
                    Log.i(
                            FOO,
                            " rootViewGroup1 Top ${view.top}"
                    )
                    Log.i(
                            FOO,
                            " rootViewGroup1 right ${view.right}"
                    )
                    Log.i(
                            FOO,
                            " rootViewGroup1 bottom ${view.bottom}"
                    )

                    Log.i(
                            FOO,
                            " rootViewGroup1 focus ${view.hasFocus()}"
                    )
                    Log.i(
                            FOO,
                            " rootViewGroup1 visibility ${view.visibility}"
                    )

                    Log.i(
                            FOO,
                            " rootViewGroup1 enable ${view.isEnabled}"
                    )


                    Log.i(
                            FOO,
                            " rootViewGroup1 focusable ${view.isFocusable}"
                    )

                    Log.i(
                            FOO,
                            " rootViewGroup1 longclicked ${view.isLongClickable}"
                    )

                    Log.i(
                            FOO,
                            " rootViewGroup1 clickable ${view.isClickable}"
                    )

                    var firstCordinates = arrayOf(view.left, view.top)


                    var secondCordinates = arrayOf(view.right, view.bottom)

                    bounds =
                            "${Arrays.toString(firstCordinates)} ${Arrays.toString(secondCordinates)}"

                    focused = view.isFocused
                    visible = view.visibility == 0
                    enabled = view.isEnabled
                    focusable = view.isFocusable
                    longClickable = view.isLongClickable
                    if (view.isScrollContainer) {
                        scrollable = true
                    }
                    isClickable = view.isClickable

                    view.findViewById<Button>(view.id)
                    viewText = (view as Button).text.toString()
                    xPath = "//hierarchy[1]/"
                    mclazz = "Button"
                    mType = "XCUIElementTypeButton"
                    ContentDesc = viewText
                    ActionValue = ""


                }
            }
            true
        }
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return localCallback!!.dispatchKeyEvent(event)
    }

    @SuppressLint("NewApi")
    override fun dispatchKeyShortcutEvent(event: KeyEvent): Boolean {
        return localCallback!!.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        val action = event.actionMasked
        var actionCode = ""
        when (action) {
            MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
            MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
            MotionEvent.ACTION_UP -> actionCode = "RELEASE"
        }
//        Log.d(FOO, "The action is : $actionCode")
//        Log.d(FOO, "The action is time stemp is  : ${System.currentTimeMillis()}")
//        Log.d(FOO, "The action is time dateformat is  : ${getDateTime(System.currentTimeMillis())}")


        val index = event.actionIndex
        var xPos = -1
        var yPos = -1

//        if (actionCode === "Down") {
//
//
////            Log.d(FOO, "xPosition: $xPos, yPosition: $yPos  $actionCode")
//        }
////        else if (actionCode === "Move") {
////            xPos = event.getX(index).toInt()
////            yPos = event.getY(index).toInt()
////            upfinalPositionx = xPos
////            upfinalPositiony = yPos
////            Log.d(FOO, "xPosition: $xPos, yPosition: $yPos  $actionCode")
////        }
////        else if (actionCode === "Up"){
////
////           Log.d(FOO, "xPosition: $xPos, yPosition: $yPos  $actionCode")
////        }
//        else if (actionCode === "Pointer Down"){
//
//           //     Log.d(FOO, "pointerdown")
//        }
//        if (downfinalPositionx == upfinalPositionx  && downfinalPositiony ==upfinalPositiony)
//        {
//            Log.d(FOO, "remove all move")
//
//        }
//        else
//        {
//        //    Log.d(FOO, "do not remove all move")
//        }


        if (localCallback!!.dispatchTouchEvent(event)) {


            //    Log.d(FOO, "Wrting on file")

//            val rootViewGroup = activity?.window?.decorView?.findViewById<ViewGroup>(R.id.)?.childCount
//            val getChildView = activity?.window?.decorView?.findViewById<ViewGroup>(R.id.content)?.children


//            val x = event.x.toInt()
//
//            for (i in 0 until rootViewGroup!!) {
////                val c: View = getChildView
////                if (x > c.left && x < c.right) {
////                    return c.onTouchEvent(event)
////                }
//            }


            when {
                actionCode === "PRESS" -> {

//                    if (v !is EditText) {


                    xPos = event.getX(index).toInt()
                    yPos = event.getY(index).toInt()


                    Log.d(FOO, "PRESS on  $xPos - $yPos")
                    mouseEventList?.add(
                            MouseEvent(
                                    System.currentTimeMillis(),
                                    factorForResolutionWidth(xPos),
                                    factorForResolutionLenght(yPos),
                                    actionCode
                            )
                    )
//                    }
                }
                actionCode === "DRAG" -> {

//                    if (v !is EditText) {


                    xPos = event.getX(index).toInt()
                    yPos = event.getY(index).toInt()
                    Log.d(FOO, "MOVE on  $xPos - $yPos")
                    mouseEventList?.add(
                            MouseEvent(
                                    System.currentTimeMillis(),
                                    factorForResolutionWidth(xPos),
                                    factorForResolutionLenght(yPos),
                                    actionCode
                            )
                    )


                }
                actionCode === "RELEASE" -> {


                    mouseEventList?.add(
                            MouseEvent(
                                    System.currentTimeMillis(),
                                    factorForResolutionWidth(xPos),
                                    factorForResolutionLenght(yPos),
                                    actionCode
                            )
                    )

                    app = App(activity)
                    var selectedComponent = SelectedComponent(
                            `package` = app?.packageName,
                            bounds = bounds.toString(),
                            uId = uId,
                            focused = focused,
                            focusable = focusable,
                            clickable = isClickable,
                            enabled = enabled,
                            longClickable = longClickable,
                            scrollable = scrollable,
                            visible = visible,
                            resourceId = "",
                            xpath = xPath,
                            text = viewText,
                            clazz = mclazz,
                            Type = mType,
                            ContentDesc = ContentDesc,
                    )

                    var device = DeviceConfigured(

                            true,
                            "",
                            d!!.manufacturer,
                            getScreenResolution(this.activity),
                            formatSize(getRamForDevice(this.activity)),
                            "DEFAULT",
                            d!!.manufacturer,
                            d!!.model,
                            d!!.board,
                            System.getProperty("os.arch"),
                            d!!.osVersion,
                            Build.VERSION.SDK_INT.toString(),
                            d!!.device,
                            "ANDROID"
                    )
                    var appInfo = AppInfo(
                            app?.packageName,
                            app?.appName,
                            app?.appVersionCode.toString(),
                            Build.VERSION.SDK_INT.toString(),
                            null,
                            null,
                            "appIconFile",
                            "appFile"
                    )

                    var action = "CLICK"
                    get64EncodedString(
                            writeJSONObjectListener,
                            selectedComponent,
                            action,
                            device,
                            appInfo
                    )


                }


//                }
            }


        }
        return true

    }

    private fun ReadJsonOnMouseEvent(obj: JSONObject) {
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
            e.printStackTrace();
        }
        var objectToBeAdded = Gson().fromJson(obj.toJSONString(), JsonObject::class.java)
        println("Input Read new object ${obj.toJSONString()}")
        println("Input Read new object $objectToBeAdded")


        val obj = convertedObject?.get("scenario")?.asJsonArray
        obj?.add(objectToBeAdded)

        writeFile(obj)
    }

    private fun writeFile(obj: JsonArray?) {
        var mouseEvent: ArrayList<MouseEvent> = arrayListOf()
        mouseEvent.add(MouseEvent(null, null, null, null))

        var d = Device(activity)
        app = App(activity)
        var device = DeviceConfigured(
                true,
                "",
                d.manufacturer,
                getScreenResolution(this.activity),
                formatSize(getRamForDevice(this.activity)),
                "DEFAULT",
                d.manufacturer,
                d.model,
                d.board,
                System.getProperty("os.arch"),
                d.osVersion,
                Build.VERSION.SDK_INT.toString(),
                d.device,
                "ANDROID"
        )
        var appInfo = AppInfo(
                app?.packageName,
                app?.appName,
                app?.appVersionCode.toString(),
                Build.VERSION.SDK_INT.toString(),
                null,
                null,
                "appIconFile",
                "appFile"
        )
//        var selectedComponent = SelectedComponent()
//        var scenario = Scenario(
//            "APP_LAUNCH",
//            "--",
//            a.activityName,
//            "XML_PLACE",
//            selectedComponent,
//            mouseEvent,
//            null,
//            null,
//            "",
//            "",
//            "",
//            AndroidPerformanceMonitors(),
//            "",
//            0,
//            null,
//            12345
//        )


        var scenarioList: ArrayList<Scenario> = arrayListOf()
        if (obj != null) {

            val jArray: JsonArray = obj as JsonArray
            val yourArray: ArrayList<Scenario> =
                    Gson().fromJson(jArray.toString(), object : TypeToken<List<Scenario?>?>() {}.type)
            for (i in 0 until yourArray.size) {
                scenarioList.add(yourArray[i])
            }


            var threshold = Threshold()

            val obj = CaseScenario(
                    true,
                    gUID,
                    packageName,
                    scenarioList,
                    device,
                    threshold,
                    appInfo

            )


            WriteCaseSenarioJson().writeCaseSenarioJson(obj.toJSON()!!, activity)
        } else {
            Log.d("Exception", "Obj is null")
        }

    }

    override fun dispatchTrackballEvent(event: MotionEvent): Boolean {
        return localCallback!!.dispatchTrackballEvent(event)
    }

    @SuppressLint("NewApi")
    override fun dispatchGenericMotionEvent(event: MotionEvent): Boolean {
        return localCallback!!.dispatchGenericMotionEvent(event)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent): Boolean {
        return localCallback!!.dispatchPopulateAccessibilityEvent(event)
    }

    override fun onCreatePanelView(featureId: Int): View? {
        return localCallback!!.onCreatePanelView(featureId)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        return localCallback!!.onCreatePanelMenu(featureId, menu)
    }

    override fun onPreparePanel(featureId: Int, view: View?, menu: Menu): Boolean {
        return localCallback!!.onPreparePanel(featureId, view, menu)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        return localCallback!!.onMenuOpened(featureId, menu)
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem): Boolean {
        return localCallback!!.onMenuItemSelected(featureId, item)
    }

    override fun onWindowAttributesChanged(attrs: WindowManager.LayoutParams) {
        localCallback!!.onWindowAttributesChanged(attrs)
    }

    override fun onContentChanged() {
        Log.d(FOO, "onContentChanged")
        localCallback!!.onContentChanged()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.d(FOO, "ttest onfocus changed called")
        localCallback!!.onWindowFocusChanged(hasFocus)
    }

    override fun onAttachedToWindow() {
        localCallback!!.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        localCallback!!.onDetachedFromWindow()
    }

    override fun onPanelClosed(featureId: Int, menu: Menu) {
        localCallback!!.onPanelClosed(featureId, menu)
    }

    override fun onSearchRequested(): Boolean {
        return localCallback!!.onSearchRequested()
    }

    override fun onSearchRequested(searchEvent: SearchEvent): Boolean {
        return false
    }

    @SuppressLint("NewApi")
    override fun onWindowStartingActionMode(callback: ActionMode.Callback): ActionMode? {
        return localCallback!!.onWindowStartingActionMode(callback)
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback, i: Int): ActionMode? {
        return null
    }

    @SuppressLint("NewApi")
    override fun onActionModeStarted(mode: ActionMode) {
        localCallback!!.onActionModeStarted(mode)
    }

    @SuppressLint("NewApi")
    override fun onActionModeFinished(mode: ActionMode) {
        localCallback!!.onActionModeFinished(mode)
    }

    private fun getDateTime(timeStamp: Long): String? {
        return try {
            val sdf = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    Locale.getDefault()
            )
            val netDate = Date(timeStamp)
            sdf.format(netDate)
        } catch (ex: java.lang.Exception) {
            "xx"
        }
    }

    fun writeFileFirstTime() {
//        var mouseEvent: ArrayList<MouseEvent> = arrayListOf()
//        mouseEvent.add(null)


        d = Device(activity)
        app = App(activity)
        var device = DeviceConfigured(
                true,
                "",
                d!!.manufacturer,
                getScreenResolution(this.activity),
                formatSize(getRamForDevice(this.activity)),
                "DEFAULT",
                d!!.manufacturer,
                d!!.model,
                d!!.board,
                System.getProperty("os.arch"),
                d!!.osVersion,
                Build.VERSION.SDK_INT.toString(),
                d!!.device,
                "ANDROID"
        )
        var appInfo = AppInfo(
                app?.packageName,
                app?.appName,
                app?.appVersionCode.toString(),
                Build.VERSION.SDK_INT.toString(),
                null,
                null,
                "appIconFile",
                "appFile"
        )
        var selectedComponent = SelectedComponent(
                `package` = app?.packageName,
                bounds = bounds.toString(),
                uId = uId,
                focused = focused,
                focusable = focusable,
                clickable = isClickable,
                enabled = enabled,
                longClickable = longClickable,
                scrollable = scrollable,
                visible = visible,
        )

        var action = "APP_LAUNCH"
        get64EncodedString(writeJSONObjectListener, selectedComponent, action, device, appInfo)


    }

    private fun get64EncodedString(
            myWindowCallbackObject: writeJSONObjectListener,
            selectedComponent: SelectedComponent,
            action: String,
            device: DeviceConfigured,
            appInfo: AppInfo
    ) {
        var writeJSONObjectListener: writeJSONObjectListener? = myWindowCallbackObject

        var bitmap: Bitmap? = null
        var view =
                (this.activity?.window?.decorView?.findViewById<View>(R.id.content) as? ViewGroup)?.getChildAt(
                        0
                )

        view?.post {
            view.height //height is ready
            view.width//height is ready

            val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable = view.background
            if (bgDrawable != null) bgDrawable.draw(canvas)
            else canvas.drawColor(Color.WHITE)
            view.draw(canvas)
            bitmap = returnedBitmap

            writeJSONObjectListener?.onSuccess(
                    convert(bitmap).toString().replace("\n", ""),
                    selectedComponent,
                    action,
                    device,
                    appInfo
            )

        }


        if (bitmap == null) {
            writeJSONObjectListener?.onFailure(null)
        }


    }


    private fun convert(bitmap: Bitmap?): String? {
        return if (bitmap == null) {
            null
        } else {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        }
    }


    val writeJSONObjectListener = object : writeJSONObjectListener {
        override fun onSuccess(
                stringValue: String?,
                selectedComponent: SelectedComponent,
                action: String?,
                device: DeviceConfigured,
                appInfo: AppInfo
        ) {
            if (action === "APP_LAUNCH") {

                var scenario = Scenario(
                        action,
                        "--",
                        "${activity?.javaClass}",
                        "XML_PLACE",
                        selectedComponent,
                        null,
                        null,
                        null,
                        stringValue,
                        stringValue,
                        stringValue,
                        null,
                        "",
                        0,
                        null,
                        12345
                )

                var scenarioList: ArrayList<Scenario> = arrayListOf()
                scenarioList.add(scenario)


                var threshold = Threshold()
                gUID = UUID.randomUUID().toString()

                packageName = "Scenario_${app?.packageName}_app_${System.currentTimeMillis()}"
                val obj = CaseScenario(
                        true,
                        gUID,
                        packageName,
                        scenarioList,
                        device,
                        threshold,
                        appInfo

                )


                WriteCaseSenarioJson().writeCaseSenarioJson(obj.toJSON()!!, activity)


            } else {
                var scenario = Scenario(
                        action,
                        ActionValue,
                        app?.activityName,
                        "XML_PLACE",
                        selectedComponent,
                        mouseEventList,
                        null,
                        null,
                        stringValue,
                        stringValue,
                        stringValue,
                        null,
                        "",
                        0,
                        null,
                        12345
                )

                ReadJsonOnMouseEvent(scenario.toJSON()!!)
                mouseEventList?.clear()

            }


        }


        override fun onFailure(stringFailed: String?) {

        }

    }

}