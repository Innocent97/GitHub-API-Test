package kyalo.innocent.githubapitest.database.all_users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel


@Dao
interface UsersDao {

    // Get all users
    @Query("SELECT * FROM allusersmodel")
    fun getAllUsers(): LiveData<List<AllUsersModel>>

    // Insert users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<AllUsersModel>)

    // Get queried followers
    @Query("SELECT * FROM allusersmodel WHERE login LIKE :searchQuery")
    fun queryUsers(searchQuery: String): LiveData<List<AllUsersModel>>

    // Insert single user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleUser(user: AllUsersModel)

    // Insert a single user
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleUserInfor(singleUser: SingleUserInformation)

    @Query("SELECT * FROM singleuserinformation WHERE username LIKE :searchName")
    fun getSingleUser(searchName: String): LiveData<SingleUserInformation>*/

    @Query("SELECT * FROM allusersmodel WHERE login LIKE :searchName")
    fun getSingleUserFromDB(searchName: String): LiveData<AllUsersModel>
}