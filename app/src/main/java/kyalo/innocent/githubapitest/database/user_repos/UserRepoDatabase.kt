package kyalo.innocent.githubapitest.database.user_repos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kyalo.innocent.githubapitest.database.organizations.OrganizationsDao
import kyalo.innocent.githubapitest.models.OrgModel

@Database(entities = [UserReposModel::class], version = 1, exportSchema = false)
abstract class UserRepoDatabase(): RoomDatabase() {
    abstract val userRepoDao: UserRepoDao
}

// initialize the database
private lateinit var INSTANCE: UserRepoDatabase

fun getUserRepoDatabase(context: Context): UserRepoDatabase {

    // Run inside synchronized block to ensure it's thread safe
    synchronized(UserRepoDatabase::class.java) {

        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                UserRepoDatabase::class.java,
                "user_repo_db"
            ).build()
        }

        return INSTANCE
    }
}