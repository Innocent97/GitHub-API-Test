package kyalo.innocent.githubapitest.database.user_repos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kyalo.innocent.githubapitest.models.OrgModel

@Dao
interface UserRepoDao {

    @Query("SELECT * FROM user_repos_table")
    fun getUsersRepos(): LiveData<List<UserReposModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRepos(userRepos: List<UserReposModel>)

    /*// Get queried organization
    @Query("SELECT * FROM orgmodel WHERE login LIKE :searchQuery")
    fun queryOrganization(searchQuery: String): LiveData<List<OrgModel>>*/
}
