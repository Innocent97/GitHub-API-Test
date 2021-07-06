package kyalo.innocent.githubapitest.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrgModel constructor(
    @PrimaryKey
    val id: Int?,
    val avatar_url: String?,
    val blog: String?,
    val company: String?,
    val created_at: String?,
    val description: String?,
    val email: String? = "",
    val events_url: String?,
    val followers: Int?,
    val following: Int?,
    val has_organization_projects: Boolean?,
    val has_repository_projects: Boolean?,
    val hooks_url: String?,
    val html_url: String?,
    val is_verified: Boolean?,
    val issues_url: String?,
    val location: String?,
    val login: String?,
    val members_url: String?,
    val name: String?,
    val node_id: String?,
    val public_gists: Int?,
    val public_members_url: String?,
    val public_repos: Int?,
    val repos_url: String?,
    val twitter_username: String?,
    val type: String?,
    val updated_at: String?,
    val url: String?
)