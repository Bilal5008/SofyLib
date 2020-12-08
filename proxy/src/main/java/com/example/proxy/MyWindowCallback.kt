package com.example.proxy

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
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
    var bounds: String? = "[0,0][0,0]"
    var uId: Int? = 0
    var focused: Boolean? = false
    var visible: Boolean? = false
    var resourceId: String? = ""
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
    var ActionValue: String? = "--"
    var Password: Boolean? = false
    var view: View? = null

    companion object {
        const val FOO = "MyWindowCallback"
        const val TOUCHCALLBACKS = "NewCallbacks"
        const val TextViewFoo = "TextView"
        const val EditTextFoo = "EditText"
    }

    constructor(localCallback: Window.Callback, activity: Activity) : this() {
        this.activity = activity
        this.localCallback = localCallback
        Log.i(FOO, "Registringcallbacks*******")

        mouseEventList = arrayListOf()
        view =
            (this.activity?.window?.decorView?.findViewById<View>(R.id.content) as? ViewGroup)?.getChildAt(
                0
            )
        if (this.activity != null && this.activity?.window?.decorView?.findViewById<View>(R.id.content) as? ViewGroup != null) {
            debugViewIds(view!!, "NEW_LOGS")
        }
    }


    private fun addTextViewListener(finalView: TextView, i: Int) {
        finalView?.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.actionMasked
            var actionCode = ""
            when (action) {
                MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
                MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
                MotionEvent.ACTION_UP -> actionCode = "RELEASE"
            }
            val index = motionEvent.actionIndex

            when {
                actionCode === "PRESS" -> {

                    mouseEventList?.clear()
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )

                }
                actionCode === "DRAG" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )
                }
                actionCode === "RELEASE" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            -1,
                            -1,
                            actionCode
                        )
                    )

                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    Log.i(TextViewFoo, " rootViewGroup1 UID ${i}")

                    uId = i
                    var firstCordinates = arrayOf(view.left, view.top)
                    var secondCordinates = arrayOf(view.right, view.bottom)
                    bounds =
                        "${Arrays.toString(firstCordinates).replace(" ", "")}${
                            Arrays.toString(
                                secondCordinates
                            ).replace(" ", "")
                        }"

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
                    viewText = (view as TextView).text.toString()
                    xPath = "//hierarchy[1]/"
                    mclazz = "TextView"
                    mType = "XCUIElementTypeTextView"
                    ContentDesc = ""
                    ActionValue = "--"

                    Password = false

                    app = App(activity)
                    print("this is Needed + $mType + $mclazz")
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
                        resourceId = resourceId,
                        xpath = xPath,
                        text = viewText,
                        clazz = mclazz,
                        Type = mType,
                        ContentDesc = ContentDesc,
                        Password = Password

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
            }
            false
        }

    }

    private fun addSetTextListener(finalView: EditText, i: Int) {


        finalView.imeOptions = EditorInfo.IME_ACTION_DONE

        finalView.setOnEditorActionListener(OnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (finalView.length() > 0) {
                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    uId = i
                    var firstCordinates = arrayOf(view.left, view.top)
                    var secondCordinates = arrayOf(view.right, view.bottom)

                    bounds =
                        "${Arrays.toString(firstCordinates).replace(" ", "")}${
                            Arrays.toString(
                                secondCordinates
                            ).replace(" ", "")
                        }"

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
                    Password = false

                    mouseEventList = ArrayList()
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
                        Password = Password


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

    private fun addOnButtonClickListener(finalView: Button, i: Int) {
        finalView?.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.actionMasked
            var actionCode = ""
            when (action) {
                MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
                MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
                MotionEvent.ACTION_UP -> actionCode = "RELEASE"
            }
            val index = motionEvent.actionIndex
            when {
                actionCode === "PRESS" -> {
                    mouseEventList?.clear()
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )
                    Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
                }
                actionCode === "DRAG" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )

                }
                actionCode === "RELEASE" -> {

                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            -1,
                            -1,
                            actionCode
                        )
                    )

                    Log.i("ActionUp  ", "Button")
                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    val location = IntArray(2)
                    Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
                    Log.i(
                        TOUCHCALLBACKS,
                        " rootViewGroup1 viewcheck ${
                            view?.getLocationOnScreen(location).toString()
                        }"
                    )
                    uId = i
                    var firstCordinates = arrayOf(view.left, view.top)
                    var secondCordinates = arrayOf(view.right, view.bottom)
                    bounds = "${Arrays.toString(firstCordinates).replace(" ", "")}${
                        Arrays.toString(
                            secondCordinates
                        ).replace(" ", "")
                    }"

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
                    ContentDesc = "Button"
                    ActionValue = "--"
                    Password = false

                    d = Device(activity)
                    app = App(activity)
                    print("this is Needed + $mType + $mclazz")
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
                        resourceId = resourceId,
                        xpath = xPath,
                        text = viewText,
                        clazz = mclazz,
                        Type = mType,
                        ContentDesc = ContentDesc,
                        Password = Password

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
            }
            false
        }
    }


    private fun addOnEditTextListener(finalView: EditText, i: Int) {
        finalView?.setOnTouchListener { view, motionEvent ->
            Log.i(FOO, "FirstAddedTOuchListener $view")

            val index = motionEvent.actionIndex
            val action = motionEvent.actionMasked
            var actionCode = ""
            when (action) {
                MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
                MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
                MotionEvent.ACTION_UP -> actionCode = "RELEASE"
            }
            when {
                actionCode === "PRESS" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )
                }
                actionCode === "DRAG" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )

                }
                actionCode === "RELEASE" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            -1,
                            -1,
                            actionCode
                        )
                    )


                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    uId = i
                    var firstCordinates = arrayOf(view.left, view.top)
                    var secondCordinates = arrayOf(view.right, view.bottom)
                    bounds =
                        "${Arrays.toString(firstCordinates).replace(" ", "")}${
                            Arrays.toString(
                                secondCordinates
                            ).replace(" ", "")
                        }"

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
                    mclazz = "EditText"
                    mType = "XCUIElementTypeEditText"
                    ContentDesc = viewText
                    ActionValue = "--"
                    if (finalView.inputType.toString().equals("129")) {
                        Password = true
                    }
                    d = Device(activity)
                    app = App(activity)
                    print("this is Needed + $mType + $mclazz")
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
                        resourceId = resourceId,
                        xpath = xPath,
                        text = viewText,
                        clazz = mclazz,
                        Type = mType,
                        ContentDesc = ContentDesc,
                        Password = Password

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
            }

            false
        }
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return localCallback!!.dispatchKeyEvent(event)
    }

    @SuppressLint("NewApi")
    override fun dispatchKeyShortcutEvent(event: KeyEvent): Boolean {
        return localCallback!!.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TOUCHCALLBACKS, "dispatchTouchEvent $view")
        localCallback!!.dispatchTouchEvent(event)
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
            visible = visible
        )

        var action = "APP_LAUNCH"
        println("action not again")
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

            val returnedBitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
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


    fun debugViewIds(view: View, logtag: String?): View {
        Log.v(logtag, "traversing: " + view.javaClass.simpleName + ", id: " + view.id)
        return if (view.parent != null && view.parent is ViewGroup) {
            if (view is ScrollView) {
                addingSwipOnLayout(view)
            }
            debugViewIds(view.parent as View, logtag)
        } else {
            debugChildViewIds(view, logtag, 0)
            return view
        }
    }

    private fun addingSwipOnLayout(view: View) {
        view.setOnTouchListener(object : OnSwipeTouchListener(activity) {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val action = event?.actionMasked
                var actionCode = ""
                when (action) {
                    MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
                    MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
                    MotionEvent.ACTION_UP -> actionCode = "RELEASE"
                }
                when {
                    actionCode === "PRESS" -> {
                        mouseEventList?.clear()
                        if (event != null) {
                            mouseEventList?.add(
                                MouseEvent(
                                    System.currentTimeMillis(),
                                    factorForResolutionWidth(event.getX().toInt()),
                                    factorForResolutionLenght(event.getY().toInt()),
                                    actionCode
                                )
                            )
                        }
                        Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
                    }
                    actionCode === "DRAG" -> {
                        mouseEventList?.add(
                            MouseEvent(
                                System.currentTimeMillis(),
                                factorForResolutionWidth(event?.getX()?.toInt()!!),
                                factorForResolutionLenght(event.getY().toInt()),
                                actionCode
                            )
                        )

                    }
                    actionCode === "RELEASE" -> {
                        mouseEventList?.add(
                            MouseEvent(
                                System.currentTimeMillis(),
                                -1,
                                -1,
                                actionCode
                            )
                        )


                        var firstCordinates = arrayOf(view.left, view.top)
                        var secondCordinates = arrayOf(view.right, view.bottom)

                        bounds = "${Arrays.toString(firstCordinates).replace(" ", "")}${
                            Arrays.toString(
                                secondCordinates
                            ).replace(" ", "")
                        }"

                        app = App(activity)
                        print("this is Needed + $mType + $mclazz")
                        var selectedComponent = SelectedComponent(
                            `package` = app?.packageName,
                            bounds = bounds.toString(),
                            uId = uId,
                            focused = focused,
                            focusable = focusable,
                            clickable = isClickable,
                            enabled = enabled,
                            longClickable = longClickable,
                             // this is changed for swipe
                            scrollable = true,
                            visible = visible,
                            resourceId = resourceId,
                            xpath = xPath,
                            text = viewText,
                            // this is changed for swipe
                            clazz = "ViewGroup",
                            Type = mType,
                            ContentDesc = ContentDesc,
                            Password = Password

                        )

                        var device = DeviceConfigured(

                            true,
                            "",
                            d!!.manufacturer,
                            getScreenResolution(activity),
                            formatSize(getRamForDevice(activity)),
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

                        get64EncodedString(
                            writeJSONObjectListener,
                            selectedComponent,
                            "SWIPE",
                            device,
                            appInfo
                        )
                    }
                }

                return super.onTouch(v, event)
            }
        })


//        view.setOnTouchListener(object : OnSwipeTouchListener(activity) {
//            override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
//
//
//                val action = motionEvent?.actionMasked
//                var actionCode = ""
//                when (action) {
//                    MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
//                    MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
//                    MotionEvent.ACTION_UP -> actionCode = "RELEASE"
//                }
//                val index = motionEvent?.actionIndex
//                when {
//                    actionCode === "PRESS" -> {
//                        mouseEventList?.clear()
//                        if (motionEvent != null) {
//                            mouseEventList?.add(
//                                MouseEvent(
//                                    System.currentTimeMillis(),
//                                    factorForResolutionWidth(motionEvent.getX(index).toInt()),
//                                    factorForResolutionLenght(motionEvent.getY(index).toInt()),
//                                    actionCode
//                                )
//                            )
//                        }
//                        Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
//                    }
//                    actionCode === "DRAG" -> {
//                        mouseEventList?.add(
//                            MouseEvent(
//                                System.currentTimeMillis(),
//                                factorForResolutionWidth(motionEvent?.getX(index)?.toInt()!!),
//                                factorForResolutionLenght(motionEvent?.getY(index)?.toInt()),
//                                actionCode
//                            )
//                        )
//
//                    }
//                    actionCode === "RELEASE" -> {
//
//                        Log.i("ActionUp  ", "Button")
//                        val rootGlobalRect = Rect()
//                        view.getGlobalVisibleRect(rootGlobalRect);
//                        val location = IntArray(2)
//                        Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
//                        Log.i(
//                            TOUCHCALLBACKS,
//                            " rootViewGroup1 viewcheck ${
//                                view?.getLocationOnScreen(location).toString()
//                            }"
//                        )
//                        uId = i
//                        var firstCordinates = arrayOf(view.left, view.top)
//                        var secondCordinates = arrayOf(view.right, view.bottom)
//                        bounds = "${Arrays.toString(firstCordinates).replace(" ", "")}${
//                            Arrays.toString(
//                                secondCordinates
//                            ).replace(" ", "")
//                        }"
//
//                        focused = view.isFocused
//                        visible = view.visibility == 0
//                        enabled = view.isEnabled
//                        focusable = view.isFocusable
//                        longClickable = view.isLongClickable
//                        if (view.isScrollContainer) {
//                            scrollable = true
//                        }
//                        isClickable = view.isClickable
//
//                        view.findViewById<Button>(view.id)
//                        viewText = (view as Button).text.toString()
//                        xPath = "//hierarchy[1]/"
//                        mclazz = "Button"
//                        mType = "XCUIElementTypeButton"
//                        ContentDesc = "Button"
//                        ActionValue = "--"
//                        Password = false
//
//                        d = Device(activity)
//                        mouseEventList?.add(
//                            MouseEvent(
//                                System.currentTimeMillis(),
//                                -1,
//                                -1,
//                                actionCode
//                            )
//                        )
//
//                        app = App(activity)
//                        print("this is Needed + $mType + $mclazz")
//                        var selectedComponent = SelectedComponent(
//                            `package` = app?.packageName,
//                            bounds = bounds.toString(),
//                            uId = uId,
//                            focused = focused,
//                            focusable = focusable,
//                            clickable = isClickable,
//                            enabled = enabled,
//                            longClickable = longClickable,
//                            scrollable = scrollable,
//                            visible = visible,
//                            resourceId = resourceId,
//                            xpath = xPath,
//                            text = viewText,
//                            clazz = mclazz,
//                            Type = mType,
//                            ContentDesc = ContentDesc,
//                            Password = Password
//
//                        )
//                        var device = DeviceConfigured(
//
//                            true,
//                            "",
//                            d!!.manufacturer,
//                            getScreenResolution(activity),
//                            formatSize(getRamForDevice(activity)),
//                            "DEFAULT",
//                            d!!.manufacturer,
//                            d!!.model,
//                            d!!.board,
//                            System.getProperty("os.arch"),
//                            d!!.osVersion,
//                            Build.VERSION.SDK_INT.toString(),
//                            d!!.device,
//                            "ANDROID"
//                        )
//                        var appInfo = AppInfo(
//                            app?.packageName,
//                            app?.appName,
//                            app?.appVersionCode.toString(),
//                            Build.VERSION.SDK_INT.toString(),
//                            null,
//                            null,
//                            "appIconFile",
//                            "appFile"
//                        )
//                        var action = "SWIPE"
//                        get64EncodedString(
//                            writeJSONObjectListener,
//                            selectedComponent,
//                            action,
//                            device,
//                            appInfo
//                        )
//
//
//
//                return super.onTouch(v, motionEvent)
//            }
//
//            override fun onSwipeRight() {
//                super.onSwipeRight()
//            }
//
//            override fun onSwipeLeft() {
//                super.onSwipeLeft()
//            }
//
//            override fun onSwipeTop() {
//                super.onSwipeTop()
//            }
//
//            override fun onSwipeBottom() {
//                super.onSwipeBottom()
//            }
//        })


    }

    private fun debugChildViewIds(view: View, logtag: String?, spaces: Int) {
        if (view is ViewGroup) {
            val group = view
            for (i in 0 until group.childCount) {
                val child = group.getChildAt(i)
                addListenering(child, i)
                Log.v(
                    logtag,
                    padString("view: " + child.javaClass.simpleName + "(" + child.id + ")", spaces)
                )
                debugChildViewIds(child, logtag, spaces + 1)
            }
            Log.v(logtag, "this is calling")


        }

    }

    private fun addListenering(finalView: View?, i: Int): Boolean {
        if (finalView is AppCompatTextView) {
            addTextViewListener(finalView, i)
        }
        if (finalView is AppCompatButton) {
            addOnButtonClickListener(finalView, i)
        }

        if (finalView is AppCompatEditText) {
            addSetTextListener(finalView, i)
            addOnEditTextListener(finalView, i)
        }
        if (finalView is AppCompatImageButton) {
            addImageButtonListener(finalView, i)
        }
        if (finalView is RecyclerView) {
            // Use
            val handler = Handler()
            handler.postDelayed({
                addRecyclerListener(finalView, i)
            }, 2000)


        }



        return true
    }

    private fun addRecyclerListener(finalView: RecyclerView, i: Int) {


        for (i in 0 until finalView.childCount) {
            println("recyclerView Click   traversing")
            finalView[i].setOnTouchListener { view, motionEvent ->
                val action = motionEvent.actionMasked
                var actionCode = ""
                when (action) {
                    MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
                    MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
                    MotionEvent.ACTION_UP -> actionCode = "RELEASE"
                }
                val index = motionEvent.actionIndex

                when {
                    actionCode === "PRESS" -> {

                        mouseEventList?.clear()
                        mouseEventList?.add(
                            MouseEvent(
                                System.currentTimeMillis(),
                                factorForResolutionWidth(motionEvent.getX(index).toInt()),
                                factorForResolutionLenght(motionEvent.getY(index).toInt()),
                                actionCode
                            )
                        )

                    }
                    actionCode === "DRAG" -> {
                        mouseEventList?.add(
                            MouseEvent(
                                System.currentTimeMillis(),
                                factorForResolutionWidth(motionEvent.getX(index).toInt()),
                                factorForResolutionLenght(motionEvent.getY(index).toInt()),
                                actionCode
                            )
                        )
                    }
                    actionCode === "RELEASE" -> {


                        mouseEventList?.add(
                            MouseEvent(
                                System.currentTimeMillis(),
                                -1,
                                -1,
                                actionCode
                            )
                        )


                        val rootGlobalRect = Rect()
                        view.getGlobalVisibleRect(rootGlobalRect);
                        Log.i(TextViewFoo, " rootViewGroup1 UID ${i}")

                        uId = i
                        var firstCordinates = arrayOf(view.left, view.top)
                        var secondCordinates = arrayOf(view.right, view.bottom)
                        bounds =
                            "${Arrays.toString(firstCordinates).replace(" ", "")}${
                                Arrays.toString(
                                    secondCordinates
                                ).replace(" ", "")
                            }"

                        focused = view.isFocused
                        visible = view.visibility == 0
                        enabled = view.isEnabled
                        focusable = view.isFocusable
                        longClickable = view.isLongClickable
                        if (view.isScrollContainer) {
                            scrollable = true
                        }
                        isClickable = view.isClickable

//                        view.findViewById<TextView>(view.id)
                        viewText = ""
                        xPath = "//hierarchy[1]/"
                        mclazz = "TextView"
                        mType = "XCUIElementTypeTextView"
                        ContentDesc = ""
                        ActionValue = "--"

                        Password = false

                        app = App(activity)
                        print("this is Needed + $mType + $mclazz")
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
///this need to be fix
                            //                          resourceId =
//                            app?.packageName + view.findViewById<AppCompatTextView>(
//                                view.id
//                            ).toString().split("app")[2].split("}")[0],
                            resourceId = app?.packageName + ":id/textView",
                            xpath = xPath,
                            text = viewText,
                            clazz = mclazz,
                            Type = mType,
                            ContentDesc = ContentDesc,
                            Password = Password

                        )
                        var device = DeviceConfigured(

                            true,
                            "",
                            d!!.manufacturer,
                            getScreenResolution(activity),
                            formatSize(getRamForDevice(activity)),
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
                }
                true
            }

        }
//
//        finalView?.addOnItemTouchListener(object : AdapterView.OnItemClickListener,
//            RecyclerView.OnItemTouchListener {
//
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                println("recyclerView Click   onItemClick")
//            }
//
//            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//                println("recyclerView Click   onInterceptTouchEvent ${rv}")
//
//                for (i in 0 until rv.childCount) {
//                    println("recyclerView Click   traversing")
//                    rv[i].setOnTouchListener { view, motionEvent ->
//                        val action = motionEvent.actionMasked
//                        var actionCode = ""
//                        when (action) {
//                            MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
//                            MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
//                            MotionEvent.ACTION_UP -> actionCode = "RELEASE"
//                        }
//                        val index = motionEvent.actionIndex
//
//                        when {
//                            actionCode === "PRESS" -> {
//
//                                mouseEventList?.clear()
//                                mouseEventList?.add(
//                                    MouseEvent(
//                                        System.currentTimeMillis(),
//                                        factorForResolutionWidth(motionEvent.getX(index).toInt()),
//                                        factorForResolutionLenght(motionEvent.getY(index).toInt()),
//                                        actionCode
//                                    )
//                                )
//
//                            }
//                            actionCode === "DRAG" -> {
//                                mouseEventList?.add(
//                                    MouseEvent(
//                                        System.currentTimeMillis(),
//                                        factorForResolutionWidth(motionEvent.getX(index).toInt()),
//                                        factorForResolutionLenght(motionEvent.getY(index).toInt()),
//                                        actionCode
//                                    )
//                                )
//                            }
//                            actionCode === "RELEASE" -> {
//
//                                val rootGlobalRect = Rect()
//                                view.getGlobalVisibleRect(rootGlobalRect);
//                                Log.i(TextViewFoo, " rootViewGroup1 UID ${i}")
//
//                                uId = i
//                                var firstCordinates = arrayOf(view.left, view.top)
//                                var secondCordinates = arrayOf(view.right, view.bottom)
//                                bounds =
//                                    "${Arrays.toString(firstCordinates).replace(" ", "")}${
//                                        Arrays.toString(
//                                            secondCordinates
//                                        ).replace(" ", "")
//                                    }"
//
//                                focused = view.isFocused
//                                visible = view.visibility == 0
//                                enabled = view.isEnabled
//                                focusable = view.isFocusable
//                                longClickable = view.isLongClickable
//                                if (view.isScrollContainer) {
//                                    scrollable = true
//                                }
//                                isClickable = view.isClickable
//
//                                view.findViewById<TextView>(view.id)
//                                viewText = (view as TextView).text.toString()
//                                xPath = "//hierarchy[1]/"
//                                mclazz = "TextView"
//                                mType = "XCUIElementTypeTextView"
//                                ContentDesc = ""
//                                ActionValue = "--"
//
//                                Password = false
//
//                                app = App(activity)
//                                print("this is Needed + $mType + $mclazz")
//                                var selectedComponent = SelectedComponent(
//                                    `package` = app?.packageName,
//                                    bounds = bounds.toString(),
//                                    uId = uId,
//                                    focused = focused,
//                                    focusable = focusable,
//                                    clickable = isClickable,
//                                    enabled = enabled,
//                                    longClickable = longClickable,
//                                    scrollable = scrollable,
//                                    visible = visible,
//                                    resourceId = resourceId,
//                                    xpath = xPath,
//                                    text = viewText,
//                                    clazz = mclazz,
//                                    Type = mType,
//                                    ContentDesc = ContentDesc,
//                                    Password = Password
//
//                                )
//                                var device = DeviceConfigured(
//
//                                    true,
//                                    "",
//                                    d!!.manufacturer,
//                                    getScreenResolution(activity),
//                                    formatSize(getRamForDevice(activity)),
//                                    "DEFAULT",
//                                    d!!.manufacturer,
//                                    d!!.model,
//                                    d!!.board,
//                                    System.getProperty("os.arch"),
//                                    d!!.osVersion,
//                                    Build.VERSION.SDK_INT.toString(),
//                                    d!!.device,
//                                    "ANDROID"
//                                )
//                                var appInfo = AppInfo(
//                                    app?.packageName,
//                                    app?.appName,
//                                    app?.appVersionCode.toString(),
//                                    Build.VERSION.SDK_INT.toString(),
//                                    null,
//                                    null,
//                                    "appIconFile",
//                                    "appFile"
//                                )
//                                var action = "CLICK"
//                                get64EncodedString(
//                                    writeJSONObjectListener,
//                                    selectedComponent,
//                                    action,
//                                    device,
//                                    appInfo
//                                )
//
//
//                            }
//                        }
//                        false
//                    }
//
//                }
//
//
//
//                return true
//            }
//
//            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
//                println("recyclerView Click   onTouchEvent")
//
//            }
//
//            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
//                println("recyclerView Click   onRequestDisallowInterceptTouchEvent")
//
//            }
//        }
//        )

    }

    private fun addImageButtonListener(finalView: AppCompatImageButton, i: Int) {
        finalView?.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.actionMasked
            var actionCode = ""
            when (action) {
                MotionEvent.ACTION_DOWN -> actionCode = "PRESS"
                MotionEvent.ACTION_MOVE -> actionCode = "DRAG"
                MotionEvent.ACTION_UP -> actionCode = "RELEASE"
            }
            val index = motionEvent.actionIndex
            when {
                actionCode === "PRESS" -> {
                    mouseEventList?.clear()
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )
                    Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
                }
                actionCode === "DRAG" -> {
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            factorForResolutionWidth(motionEvent.getX(index).toInt()),
                            factorForResolutionLenght(motionEvent.getY(index).toInt()),
                            actionCode
                        )
                    )

                }
                actionCode === "RELEASE" -> {


                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            -1,
                            -1,
                            actionCode
                        )
                    )

                    Log.i("ActionUp  ", "Button")
                    val rootGlobalRect = Rect()
                    view.getGlobalVisibleRect(rootGlobalRect);
                    val location = IntArray(2)
                    Log.i(TOUCHCALLBACKS, " rootViewGroup1 viewcheck ${view?.visibility}")
                    Log.i(
                        TOUCHCALLBACKS,
                        " rootViewGroup1 viewcheck ${
                            view?.getLocationOnScreen(location).toString()
                        }"
                    )
                    uId = i
                    var firstCordinates = arrayOf(view.left, view.top)
                    var secondCordinates = arrayOf(view.right, view.bottom)
                    bounds =
                        "${Arrays.toString(firstCordinates).replace(" ", "")}${
                            Arrays.toString(
                                secondCordinates
                            ).replace(" ", "")
                        }"

                    focused = view.isFocused
                    visible = view.visibility == 0
                    enabled = view.isEnabled
                    focusable = view.isFocusable
                    longClickable = view.isLongClickable
                    if (view.isScrollContainer) {
                        scrollable = true
                    }
                    isClickable = view.isClickable


                    viewText = ""
                    xPath = "//hierarchy[1]/"
                    mclazz = "ImageButton"
                    mType = "XCUIElementTypeImageButtonView"
                    ContentDesc = ""
                    // this need to fix after
                    resourceId =
                        app?.packageName + view.findViewById<AppCompatImageButton>(
                            view.id
                        ).toString().split("app")[2].split("}")[0]
                    ActionValue = "--"
                    Password = false

                    d = Device(activity)
                    mouseEventList?.add(
                        MouseEvent(
                            System.currentTimeMillis(),
                            -1,
                            -1,
                            actionCode
                        )
                    )

                    app = App(activity)
                    print("this is Needed + $mType + $mclazz")
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
                        resourceId = resourceId,
                        xpath = xPath,
                        text = viewText,
                        clazz = mclazz,
                        Type = mType,
                        ContentDesc = ContentDesc,
                        Password = Password

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
            }
            true
        }

    }

    private fun padString(str: String, noOfSpaces: Int): String {
        if (noOfSpaces <= 0) {
            return str
        }
        val builder = StringBuilder(str.length + noOfSpaces)
        for (i in 0 until noOfSpaces) {
            builder.append(' ')
        }
        return builder.append(str).toString()
    }

}