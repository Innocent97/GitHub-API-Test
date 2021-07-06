package kyalo.innocent.githubapitest.database.organizations

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kyalo.innocent.githubapitest.database.github_users.GitHubUserDao
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel


@Database(entities = [OrgModel::class], version = 1, exportSchema = false)
abstract class OrganizationsDatabase(): RoomDatabase() {
    abstract val organizationDao: OrganizationsDao
}

// initialize the database
private lateinit var INSTANCE: OrganizationsDatabase

fun getGitHubUsersDatabase(context: Context): OrganizationsDatabase {

    // Run inside synchronized block to ensure it's thread safe
    synchronized(OrganizationsDatabase::class.java) {

        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                OrganizationsDatabase::class.java,
                "org_db"
            ).build()
        }

        return INSTANCE
    }
}