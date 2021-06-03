package kyalo.innocent.githubapitest.ui.github_user_profile

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kyalo.innocent.githubapitest.database.github_users.getGitHubUsersDatabase
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel
import kyalo.innocent.githubapitest.repositories.GitHubUserRepository
import java.io.IOException

class GitHubUserViewModel(application: Application): AndroidViewModel(application) {

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

    private var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private var _gitHubUser = MutableLiveData<GitHubUserModel>()
    val gitHubUser: LiveData<GitHubUserModel>
        get() = _gitHubUser

    // Get DB and Repository
    private val database = getGitHubUsersDatabase(application)
    private val gitHubUserRepository = GitHubUserRepository(database)

    var users = gitHubUserRepository.listOfUsers

    // Set value to the username
    fun setUsername(name: String) {
        _username.value = name
    }

    init {
        _username.value?.let { fetchGitHubUserInformation(it) }
        _username.value?.let { searchData(it) }
    }

    // Initialilize remote search
    fun searchData(name: String) {
        _gitHubUser.value = name.let { searchGitHubUsersRemotely(it) }
    }

    // Search User Remotely
    fun searchGitHubUsersRemotely(searchQuery: String): GitHubUserModel? {
        var searchResult: GitHubUserModel? = null
        viewModelScope.launch {
            searchResult = gitHubUserRepository.searchUserRemotely(searchQuery)!!
        }
        return searchResult
    }

    // Search User in Database
    fun searchUserInDB(searchQuery: String): LiveData<GitHubUserModel>?{
        var searchResult: LiveData<GitHubUserModel>? = null
        viewModelScope.launch {
            searchResult = gitHubUserRepository.getSingleGitHubUserDB(searchQuery)
        }
        return searchResult
    }

    // Fetch user details remotely
    private fun fetchGitHubUserInformation(login: String){
        viewModelScope.launch(Dispatchers.IO) {
            gitHubUserRepository.getSingleGitHubUser(login)
            //gitHubUser = gitHubUserRepository.getSingleGitHubUserDB(login)
            /*try {


                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
               _eventNetworkError.value = true
            }*/
        }
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

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}