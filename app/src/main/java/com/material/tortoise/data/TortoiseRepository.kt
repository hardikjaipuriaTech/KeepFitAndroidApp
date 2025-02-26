package com.material.tortoise.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


class TortoiseRepository(private val tortoiseDao: TortoiseDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val readAllGoals: LiveData<List<Goals>> = tortoiseDao.getAllGoals()
    val readAllGoalHistory: LiveData<List<GoalHistory>> = tortoiseDao.getAllGoalHistory()

    fun getActiveGoal(): MutableLiveData<Goals>? {
        val data: MutableLiveData<Goals> = MutableLiveData<Goals>()
        data.setValue(tortoiseDao.findActiveGoal().value)
        return data
    }

    suspend fun addGoal(newGoal: Goals) {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.addGoal(newGoal)
        }
    }

    suspend fun updateGoal(newGoal: Goals) {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.updateGoals(newGoal)
        }
    }

    suspend fun deleteGoal(newGoal: Goals) {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.deleteGoals(newGoal)
        }
    }

     fun deleteAllGoals() {
        tortoiseDao.deleteAllGoals()
    }

     fun findGoalByGoalName(goalName: String)= tortoiseDao.findGoalByGoalName(goalName)

     fun findActiveGoal() = tortoiseDao.findActiveGoal()

    suspend fun addGoalHistory(newGoalHistory: GoalHistory) {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.addGoalHistory(newGoalHistory)
        }
    }

    suspend fun updateGoalHistory(newGoalHistory: GoalHistory) {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.updateGoalHistory(newGoalHistory)
        }
    }

    suspend fun deleteGoalHistory(newGoalHistory: GoalHistory) {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.deleteGoalHistory(newGoalHistory)
        }
    }

     fun deleteAllGoalHistory() {
        coroutineScope.launch(Dispatchers.IO) {
            tortoiseDao.deleteAllGoalHistory()
        }
    }

    fun findGoalHistoryByDate(dateInString: String)= tortoiseDao.findGoalHistoryByDate(dateInString)



    /**
     * Sets  the goal as active.
     */
    //fun setGoalActivation(statusFlag: Boolean){}
}