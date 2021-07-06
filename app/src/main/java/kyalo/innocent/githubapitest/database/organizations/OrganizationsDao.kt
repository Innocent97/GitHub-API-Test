package kyalo.innocent.githubapitest.database.organizations

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel

@Dao
interface OrganizationsDao {

    @Query("SELECT * FROM orgmodel")
    fun getAllOrganizations(): LiveData<List<OrgModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrganization(organizationData: OrgModel)

    // Get queried organization
    @Query("SELECT * FROM orgmodel WHERE login LIKE :searchQuery")
    fun queryOrganization(searchQuery: String): LiveData<List<OrgModel>>
}