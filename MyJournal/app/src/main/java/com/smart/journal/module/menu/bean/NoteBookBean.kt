package com.smart.journal.module.menu.bean

/**
 * @author chenguandong
 * @date 2019/12/6
 * @desc
 * @email chenguandong@outlook.com
 */
class NoteBookBean {
    var noteName: String? = null
    var count: Int? = 0

    constructor(noteName: String?, count: Int?) {
        this.noteName = noteName
        this.count = count
    }
}