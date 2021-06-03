package kyalo.innocent.githubapitest.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kyalo.innocent.githubapitest.database.all_users.AllUsersDatabase
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.network.ApiClient
import kyalo.innocent.githubapitest.network.GitHubService
import retrofit2.await

class UsersRepository(private val usersDatabase: AllUsersDatabase) {

    // Repos live data property
    val users: LiveData<List<AllUsersModel>> = usersDatabase.usersDao.getAllUsers()

    // Fetch reposList from network
    suspend fun fetchAllUsers() {
        withContext(Dispatchers.IO) {
            val gitHubService = ApiClient.apiClient?.create(GitHubService::class.java)
            val apiCall = gitHubService?.getAllUsers()

            val listOfUsers = apiCall?.await()

            val listOfName = listOfUsers?.let { getListOfNames(it) }

            //listOfUsers?.let { fetchFollowers(it) }
            listOfUsers?.let { usersDatabase.usersDao.insertAllUsers(it) }
        }
    }

    // Get the list of users
    fun getListOfNames(list: List<AllUsersModel>): List<String> {
        val namesOfUsers = mutableListOf<String>()

        for (userPosition in 0..list.size-1) {
            val singleUser = list.get(userPosition).login
            singleUser?.let { namesOfUsers.add(it) }
        }

        return namesOfUsers
    }

    // Query DB for users
    fun searchDatabase(searchQuery: String): LiveData<List<AllUsersModel>> = usersDatabase.usersDao.queryUsers(searchQuery)

    // Search user remotely & insert record in DB
    suspend fun searchUser(query: String): AllUsersModel? {
        var userModel: AllUsersModel? = null

        withContext(Dispatchers.IO) {
            val gitHubService = ApiClient.apiClient?.create(GitHubService::class.java)
            val apiCall = gitHubService?.getUserRemotely(query)

            val singleUser = apiCall?.await()
            singleUser?.let { usersDatabase.usersDao.insertSingleUser(it) }

            if (singleUser != null) {
                userModel = singleUser
            }
        }
        return userModel
    }

    fun getSingleUser(userId: String): LiveData<AllUsersModel> = usersDatabase.usersDao.getSingleUserFromDB(userId)
}