package com.kisnahc.memo_kotlin_android

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Long?,
    var memo : String = "")