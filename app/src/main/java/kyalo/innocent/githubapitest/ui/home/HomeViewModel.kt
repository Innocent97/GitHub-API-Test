package kyalo.innocent.githubapitest.ui.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kyalo.innocent.githubapitest.database.all_users.getAllUsersDatabase
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.repositories.UsersRepository
import java.io.IOException

class HomeViewModel(application: Application): AndroidViewModel(application) {

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    // Get database & repository
    private val database = getAllUsersDatabase(application)
    private val usersRepository = UsersRepository(database)

    var usersList = usersRepository.users

    // Connectivity variables
    private var _connectivity = MutableLiveData<Boolean>()
    val connectivity: LiveData<Boolean>
        get() = _connectivity

    @RequiresApi(Build.VERSION_CODES.M)
    val connected = isOnline(application.applicationContext)

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                usersRepository.fetchAllUsers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(usersList.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun searchDatabase(searchQuery: String): LiveData<List<AllUsersModel>> = usersRepository.searchDatabase(searchQuery)

    // Search Remote DB
    fun searchUserRemotely(searchQuery: String): AllUsersModel? {
        var searchResult: AllUsersModel? = null
        viewModelScope.launch {
            searchResult = usersRepository.searchUser(searchQuery)!!
        }
        return searchResult
    }

    // Check Internet Connectivity
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    //fun getTotalFollowers(searchQuery: String) = usersRepository.getNumberFollowers(searchQuery)
}