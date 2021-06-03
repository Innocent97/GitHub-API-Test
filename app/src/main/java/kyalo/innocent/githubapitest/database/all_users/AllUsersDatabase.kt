package kyalo.innocent.githubapitest.database.all_users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel

@Database(entities = [AllUsersModel::class], version = 1, exportSchema = false)
abstract class AllUsersDatabase(): RoomDatabase() {
    abstract val usersDao: UsersDao
}

// initialize the database
private lateinit var INSTANCE: AllUsersDatabase

fun getAllUsersDatabase(context: Context): AllUsersDatabase {

    // Run inside synchronized block to ensure it's thread safe
    synchronized(AllUsersDatabase::class.java) {

        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AllUsersDatabase::class.java,
                "all_users_db"
            ).build()
        }

        return INSTANCE
    }
}