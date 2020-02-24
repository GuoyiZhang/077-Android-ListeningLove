package com.guoyi.listeninglove.bean

import org.litepal.crud.LitePalSupport

class FolderInfo : LitePalSupport {

    val folderName: String
    val folderPath: String
    val songCount: Int

    constructor(_folderName: String, _folderPath: String, _songCount: Int) {
        this.folderName = _folderName
        this.folderPath = _folderPath
        this.songCount = _songCount
    }
}
