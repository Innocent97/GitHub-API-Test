package kyalo.innocent.githubapitest.ui.repositories

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
import kotlinx.coroutines.launch
import kyalo.innocent.githubapitest.database.organizations.getGitHubUsersDatabase
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel
import kyalo.innocent.githubapitest.repositories.OrganizationRepository

class OrganizationsViewModel(application: Application): AndroidViewModel(application) {
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
    private var _fOrganization = MutableLiveData<List<OrgModel>>()
    lateinit var fOrganizations: LiveData<List<OrgModel>>

    // connect to db & repository
    private val fOrganizationsDatabase = getGitHubUsersDatabase(application.applicationContext)
    private val fOrganizationsRepo = OrganizationRepository(fOrganizationsDatabase)

    @RequiresApi(Build.VERSION_CODES.M)
    val connected = isOnline(application.applicationContext)

    init {
       refreshDatFromRepository()
    }

    // get data from DB
    fun refreshDatFromRepository() {
        viewModelScope.launch {
            fOrganizations = fOrganizationsRepo.listOfOrganizations
        }
    }

    // Get live data of organization from DB
    fun getOrgLocally(name: String): LiveData<List<OrgModel>> = fOrganizationsRepo.queryOrganizationLocally(name)

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    // Remotely search organization
    fun queryOrganizationRemotely(query: String): OrgModel? {
        var localOrganization: OrgModel? = null

        viewModelScope.launch {
            localOrganization = fOrganizationsRepo.searchOrganizationRemote(query)!!
        }

        return localOrganization
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

}