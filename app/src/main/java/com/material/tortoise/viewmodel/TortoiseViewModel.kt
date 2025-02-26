package com.material.tortoise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.material.tortoise.data.GoalHistory
import com.material.tortoise.data.Goals
import com.material.tortoise.data.TortoiseRepository
import com.material.tortoise.view.TortoiseDataStorePreferencesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TortoiseViewModel(
    private val tortoiseRepository: TortoiseRepository,
    private val dataStorePrefRepo: TortoiseDataStorePreferencesRepo
) : ViewModel() {

    //val _allGoalsList: LiveData<List<Goals>> = tortoiseRepository.readAllGoals

    private val _activeGoal = tortoiseRepository.findActiveGoal()

    val activeGoal: LiveData<Goals>
        get() = _activeGoal

    private val _allGoalList = tortoiseRepository.readAllGoals

    val allGoalList: LiveData<List<Goals>>
        get() = _allGoalList


    private val _addStepsState = MutableLiveData<Int>()
    val addStepsState: LiveData<Int> = _addStepsState

  // private val _historyDatePick = MutableLiveData<String>()
   //var historyDatePick: LiveData<String>
     //  get() = _historyDatePick


    fun getActiveGoal(){
        viewModelScope.launch(Dispatchers.IO) {
          //  _activeGoal.postValue(Resource.Loading())
            /*repository.getAllTasks().collect {
                if (sort)
                    _tasks.value = Resource.Success(sortTasks(it))
                else
                    _tasks.value = Resource.Success(it)
            }*/
        }

    }


    val goalsList: LiveData<List<Goals>> = tortoiseRepository.readAllGoals
    val goalHistoryList: LiveData<List<GoalHistory>> = tortoiseRepository.readAllGoalHistory

    fun findGoalByGoalName(goalName: String) = tortoiseRepository.findGoalByGoalName(goalName)
    fun findGoalHistoryByDate(dateInString: String) = tortoiseRepository.findGoalHistoryByDate(dateInString)
    fun findActiveGoal() = tortoiseRepository.findActiveGoal()

    fun deleteAllGoalHistory() {
        tortoiseRepository.deleteAllGoalHistory()
    }


    fun getAllGoals() {
        tortoiseRepository.readAllGoals
    }
    fun getAllGoalHistory() {
        tortoiseRepository.readAllGoalHistory
    }

    fun addGoals(goals: Goals) {
        viewModelScope.launch(Dispatchers.IO) {
            tortoiseRepository.addGoal(goals)
            getAllGoals()
        }
    }

    fun updateGoalDetails(goals: Goals) {
        viewModelScope.launch(Dispatchers.IO) {
            tortoiseRepository.updateGoal(goals)
            getAllGoals()
        }
    }

    fun deleteGoals(goals: Goals) {
        viewModelScope.launch(Dispatchers.IO) {
            tortoiseRepository.deleteGoal(goals)
        }
        getAllGoals()
    }

    fun addGoalHistory(goalHistory: GoalHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            tortoiseRepository.addGoalHistory(goalHistory)
            getAllGoalHistory()
        }
    }

    fun updateGoalHistory(goalHistory: GoalHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            tortoiseRepository.updateGoalHistory(goalHistory)
            getAllGoalHistory()
        }
    }

    fun deleteGoalHistory(goalHistory: GoalHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            tortoiseRepository.deleteGoalHistory(goalHistory)
            getAllGoalHistory()
        }
    }

    fun toggleOtherSwitches(goalItem: Goals) {
    }

    fun toggleGoalEditable(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePrefRepo.toggleGoalsEditable()
        }
    }
    fun toggleHistoryEditable(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePrefRepo.toggleHistoryEditable()
        }
    }

    fun isGoalEditale(): Boolean{
        var flag : Boolean = true
        viewModelScope.launch() {
            dataStorePrefRepo.isGoalEditable().collect(){
                flag=  it
            }

        }
        return flag
    }

    fun isHistoryEditale(): Boolean{
        var flag : Boolean = true
        viewModelScope.launch() {
            dataStorePrefRepo.isHistoryEditable().collect(){
                flag=  it
            }

        }
        return flag
    }
}
