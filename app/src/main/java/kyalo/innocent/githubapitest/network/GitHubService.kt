package kyalo.innocent.githubapitest.network

import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel
import kyalo.innocent.githubapitest.models.repos_models.RepositoryItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    // Get a repository
    @GET("/orgs/Microsoft/repos")
    fun getRepositories(): Call<List<RepositoryItem>>

    // Get all users
    @GET("/users")
    fun getAllUsers(): Call<List<AllUsersModel>>

    // Get list of followers
    @GET("/{name}/followers")
    fun getFollowers(@Path("name") username: String): Call<List<AllUsersModel>>

    // Search user remotely
    @GET("/users/{username}")
    fun getUserRemotely(@Path("username") name: String): Call<AllUsersModel>

    @GET("/users/{username}")
    fun getGitHubUserRemotely(@Path("username") name: String): Call<GitHubUserModel>

    // Get User infor
    @GET("/users/{username}")
    fun getGitHubUserInformation(@Path("username") name: String): Call<GitHubUserModel>

}
