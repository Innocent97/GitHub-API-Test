package kyalo.innocent.githubapitest.database.github_users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel

@Database(entities = [GitHubUserModel::class], version = 1, exportSchema = false)
abstract class GitHubUsersDatabase(): RoomDatabase() {
    abstract val gitHubUserDao: GitHubUserDao
}

// initialize the database
private lateinit var INSTANCE: GitHubUsersDatabase

fun getGitHubUsersDatabase(context: Context): GitHubUsersDatabase {

    // Run inside synchronized block to ensure it's thread safe
    synchronized(GitHubUsersDatabase::class.java) {

        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GitHubUsersDatabase::class.java,
                "github_users_db"
            ).build()
        }

        return INSTANCE
    }
}