package kyalo.innocent.githubapitest.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kyalo.innocent.githubapitest.database.github_users.GitHubUsersDatabase
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel
import kyalo.innocent.githubapitest.network.ApiClient
import kyalo.innocent.githubapitest.network.GitHubService
import retrofit2.await

class GitHubUserRepository(private val gitHubUsersDatabase: GitHubUsersDatabase) {

    var listOfUsers: LiveData<List<GitHubUserModel>> = gitHubUsersDatabase.gitHubUserDao.getAllUsers()

    // fetch single user from server
    suspend fun getSingleGitHubUser(name: String) {
        withContext(Dispatchers.IO) {
            val gitHubService = ApiClient.apiClient?.create(GitHubService::class.java)
            val apiCall = gitHubService?.getGitHubUserInformation(name)

            val singleUser = apiCall?.await()

            // insert user to DB
            singleUser?.let { gitHubUsersDatabase.gitHubUserDao.insertGitHubUser(it) }
        }
    }

    // Search user remotely & insert record in DB
    suspend fun searchUserRemotely(query: String): GitHubUserModel? {
        var userModel: GitHubUserModel? = null

        withContext(Dispatchers.IO) {
            val gitHubService = ApiClient.apiClient?.create(GitHubService::class.java)
            val apiCall = gitHubService?.getGitHubUserRemotely(query)

            val singleUser = apiCall?.await()
            singleUser?.let { gitHubUsersDatabase.gitHubUserDao.insertGitHubUser(it) }

            if (singleUser != null) {
                userModel = singleUser
            }
        }
        return userModel
    }


    // fetch single user from DB
    fun getSingleGitHubUserDB(name: String) : LiveData<GitHubUserModel> =
        gitHubUsersDatabase.gitHubUserDao.queryUserFromDB(name)

}