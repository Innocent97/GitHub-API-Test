package kyalo.innocent.githubapitest.database.github_users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel

@Dao
interface GitHubUserDao {

    // Insert single user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGitHubUser(user: GitHubUserModel)

    // Get single user from DB
    @Query("SELECT * FROM githubusermodel WHERE login LIKE :username")
    fun queryUserFromDB(username: String): LiveData<GitHubUserModel>

    @Query("SELECT EXISTS(SELECT * FROM githubusermodel)")
    fun hasItem(): Boolean

    @Query("SELECT * FROM githubusermodel")
    fun getAllUsers(): LiveData<List<GitHubUserModel>>
}