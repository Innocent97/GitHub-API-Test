package kyalo.innocent.githubapitest.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kyalo.innocent.githubapitest.database.organizations.OrganizationsDatabase
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel
import kyalo.innocent.githubapitest.network.ApiClient
import kyalo.innocent.githubapitest.network.GitHubService
import retrofit2.await

class OrganizationRepository(private val organizationsDatabase: OrganizationsDatabase) {

    var listOfOrganizations: LiveData<List<OrgModel>> = organizationsDatabase.organizationDao.getAllOrganizations()

    // Query DB for organization
    fun queryOrganizationLocally(query: String): LiveData<List<OrgModel>> =
        organizationsDatabase.organizationDao.queryOrganization(query)

    // fetch single organization from server & insert in DB
    suspend fun searchOrganizationRemote(name: String): OrgModel? {
        var searchedOrg: OrgModel? = null

        withContext(Dispatchers.IO) {
            val gitHubService = ApiClient.apiClient?.create(GitHubService::class.java)
            val apiCall = gitHubService?.getOrganization(name)

            val organization = apiCall?.await()

            // insert user to DB
            organization?.let { organizationsDatabase.organizationDao.insertOrganization(it) }

            if (organization != null) {

                searchedOrg = organization
            }
        }

        return searchedOrg
    }
}