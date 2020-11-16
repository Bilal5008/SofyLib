package com.example.model


class SelectedComponent {






    // new constructor
    constructor(
            `package`: String?,
            bounds: String?,
            uId: Int?,
            focused: Boolean?,
            visible: Boolean?,
            enabled: Boolean?,
            focusable: Boolean?,
            clickable: Boolean?,
            longClickable: Boolean?,
            scrollable: Boolean?,
            resourceId : String?,
            xpath: String?,
            text: String?,
            clazz: String ?,
            Type : String ?,
            ContentDesc : String?,





    ) {


        this.`package` = `package`
        this.bounds = bounds
        this.uid = uId
        this.focused = focused!!
        this.visible = visible!!
        this.enabled = enabled!!
        this.focusable = focusable!!
        this.clickable = clickable!!
        this.longClickable = longClickable!!
        this.scrollable = scrollable!!
        this.resourceId = resourceId!!
        this.xpath = xpath!!
        this.text = text!!
        this.clazz = clazz!!
        this.type = Type!!
        this.contentDesc = ContentDesc!!



    }


//    constructor(
//        `package`: String?,
//        bounds: String?,
//        uId: Int?,
//        focused: Boolean?,
//        visible: Boolean?,
//        enabled: Boolean?,
//        focusable: Boolean?,
//        clickable: Boolean?,
//        longClickable: Boolean?,
//        scrollable: Boolean?,
//        resourceId : String?,
//        xpath: String?,
//        text: String?
//    ) {
//
//
//        this.`package` = `package`
//        this.bounds = bounds
//        this.uid = uId
//        this.focused = focused!!
//        this.visible = visible!!
//        this.enabled = enabled!!
//        this.focusable = focusable!!
//        this.clickable = clickable!!
//        this.longClickable = longClickable!!
//        this.scrollable = scrollable!!
//        this.resourceId = resourceId!!
//        this.xpath = xpath!!
//        this.text = text!!
//
//
//    }
    constructor(
        `package`: String?,
        bounds: String?,
        uId: Int?,
        focused: Boolean?,
        visible: Boolean?,
        enabled: Boolean?,
        focusable: Boolean?,
        clickable: Boolean?,
        longClickable: Boolean?,
        scrollable: Boolean?,


    ) {


        this.`package` = `package`
        this.bounds = bounds
        this.uid = uId
        this.focused = focused!!
        this.visible = visible!!
        this.enabled = enabled!!
        this.focusable = focusable!!
        this.clickable = clickable!!
        this.longClickable = longClickable!!
        this.scrollable = scrollable!!




    }
    //    constructor(
//        instance: Int,
//        `package`: String?,
//        type: String?,
//        size: Int,
//        index: Int,
//        bounds: String?,
//        focused: Boolean,
//        visible: Boolean,
//        enabled: Boolean,
//        focusable: Boolean,
//        groupId: Int,
//        stateString: String?,
//        edittextGroup: Boolean,
//        componentId: Int,
//        inside: Boolean,
//        naf: Boolean,
//        text: String?,
//        selected: Boolean,
//        checked: Boolean,
//        password: Boolean,
//        clazz: String?,
//        clickable: Boolean,
//        contentDesc: String?,
//        xpath: String?,
//        xpathChecksum: String?,
//        prioritySize: Int,
//        training: Boolean,
//        groupPriority: Int,
//        uid: Int,
//        checkable: Boolean,
//        parentUID: Int,
//        longClickable: Boolean,
//        microVisible: Boolean,
//        scrollable: Boolean,
//        useEnterKey: Boolean,
//        vertexStates: List<String>?,
//        resourceId: String?,
//        stateStringWithoutBounds: String?,
//        activeFortraversal: Boolean,
//        calculatedActionable: Boolean,
//        crawlerTraversal: Boolean,
//        calculatedPriority: Int,
//        discoveryStage: String?,
//        calculatedSize: Int,
//        allowManyTraversal: Boolean
//    ) {
//        this.actionSteps = actionSteps
//        this.instance = instance
//        this.`package` = `package`
//        this.type = type
//        this.size = size
//        this.index = index
//        this.bounds = bounds
//        this.focused = focused
//        this.visible = visible
//        this.enabled = enabled
//        this.focusable = focusable
//        this.groupId = groupId
//        this.stateString = stateString
//        this.edittextGroup = edittextGroup
//        this.componentId = componentId
//        this.inside = inside
//        this.naf = naf
//        this.text = text
//        this.selected = selected
//        this.checked = checked
//        this.password = password
//        this.clazz = clazz
//        this.clickable = clickable
//        this.contentDesc = contentDesc
//        this.xpath = xpath
//        this.xpathChecksum = xpathChecksum
//        this.prioritySize = prioritySize
//        this.training = training
//        this.groupPriority = groupPriority
//        this.uid = uid
//        this.checkable = checkable
//        this.parentUID = parentUID
//        this.longClickable = longClickable
//        this.microVisible = microVisible
//        this.scrollable = scrollable
//        this.useEnterKey = useEnterKey
//        this.vertexStates = vertexStates
//        this.resourceId = resourceId
//        this.stateStringWithoutBounds = stateStringWithoutBounds
//        this.activeFortraversal = activeFortraversal
//        this.calculatedActionable = calculatedActionable
//        this.crawlerTraversal = crawlerTraversal
//        this.calculatedPriority = calculatedPriority
//        this.discoveryStage = discoveryStage
//        this.calculatedSize = calculatedSize
//        this.allowManyTraversal = allowManyTraversal
//    }

    var size = 0
    var index = 0
    var bounds: String? = ""
    var focused = false
    var visible = false
    var enabled = false
    var focusable = false
    var groupId = 0
    var stateString: String? = ""
    var edittextGroup = false
    var componentId = 0
    var inside = false
    var naf = false
    var text: String? = ""
    var selected = false
    var checked = false
    var password = false
    var clazz: String? = "dummy"
    var clickable = false
    var contentDesc: String? = ""
    var xpath: String? = "dummy"
    var xpathChecksum: String? = "1"
    var prioritySize = 0
    var training = false
    var groupPriority = 0
    var uid: Int? = 0
    var checkable = false
    var parentUID = 0
    var longClickable = false
    var microVisible = false
    var scrollable = false
    var useEnterKey = false
    var vertexStates: Array<String>? = arrayOf()
    var resourceId: String? = null
    var stateStringWithoutBounds: String? = ""
    var activeFortraversal = false
    var calculatedActionable = false
    var crawlerTraversal = false
    var calculatedPriority = 0
    var discoveryStage: String? = null
    var calculatedSize = 0
    var allowManyTraversal = false
    var actionSteps: ActionSteps? = ActionSteps()
    var instance = 0
    var `package`: String? = null
    var type: String? = ""


}