package com.kzdev.appcursolucas.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    //auto generate serve para q a propria room forneça um id
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "note") val note: String?,
)