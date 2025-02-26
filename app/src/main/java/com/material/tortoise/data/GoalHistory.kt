package com.material.tortoise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goalHistory")
data class GoalHistory(

    @ColumnInfo(name = "goalName")
    var goalName: String?,

    @ColumnInfo(name = "statusFlag")
    var statusFlag: Boolean?,

    @ColumnInfo(name = "stepsCount")
    var stepsCount: Int?,

    @ColumnInfo(name = "targetedSteps")
    var targetedSteps: Int?,

    @ColumnInfo(name = "dateCreated")
    var dateCreated: Date?,

    @ColumnInfo(name = "updatedDate")
    var updatedDate: Date?,

    @ColumnInfo(name = "dateInString")
    var dateInString: String?

){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}