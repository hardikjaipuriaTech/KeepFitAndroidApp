package com.material.tortoise.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface TortoiseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoal(goals: Goals)

    @Update
    suspend fun updateGoals(goals: Goals)

    @Delete
    suspend fun deleteGoals(goals: Goals)

    @Query("SELECT * FROM goals order by statusFlag desc")
    fun getAllGoals(): LiveData<List<Goals>>

    @Query("SELECT * FROM goals WHERE goalName = :goalName")
    fun findGoalByGoalName(goalName: String): LiveData<Goals>

    @Query("SELECT * FROM goals WHERE statusFlag = 1")
    fun findActiveGoal(): LiveData<Goals>

    @Query("DELETE FROM goals")
    fun deleteAllGoals()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoalHistory(goalHistory: GoalHistory)

    @Update
    suspend fun updateGoalHistory(goalHistory: GoalHistory)

    @Delete
    suspend fun deleteGoalHistory(goalHistory: GoalHistory)

    @Query("SELECT * FROM goalHistory order by dateCreated desc")
    fun getAllGoalHistory(): LiveData<List<GoalHistory>>

    @Query("SELECT * FROM goalHistory WHERE dateInString = :dateInString")
    fun findGoalHistoryByDate(dateInString: String): LiveData<GoalHistory>

    @Query("DELETE FROM goalHistory")
    fun deleteAllGoalHistory()

}