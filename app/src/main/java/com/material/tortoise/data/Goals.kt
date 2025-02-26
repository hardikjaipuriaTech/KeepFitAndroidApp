package com.material.tortoise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goals(
    @PrimaryKey
    @ColumnInfo(name = "goalName")
    var goalName: String,
   // @ColumnInfo(name = "dateAdded")
   // val dateAdded: Date
    @ColumnInfo(name = "goalDescription")
    var goalDescription:String,

    @ColumnInfo(name = "statusFlag")
    var statusFlag: Boolean,

    @ColumnInfo(name = "stepsCount")
    var stepsCount: Int,

    @ColumnInfo(name = "targetedSteps")
    var targetedSteps: Int,

    @ColumnInfo(name = "dateCreated")
    var dateCreated: String,

    @ColumnInfo(name = "updatedDate")
    var updatedDate: String

)