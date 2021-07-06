package kyalo.innocent.githubapitest.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrganizationModel(
    @PrimaryKey
    val id: Int,

    val location: String,
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val email: String,
    val events_url: String,
    val url: String,
    val twitter_username: String,
    val public_repos: Int,
    val total_private_repos: Int,

    val billing_email: String? = "",
    val blog: String,
    val collaborators: Int,
    val company: String,
    val created_at: String,
    val default_repository_permission: String,
    val description: String,
    val disk_usage: Int,


    val has_organization_projects: Boolean,
    val has_repository_projects: Boolean,
    val hooks_url: String,
    val html_url: String,

    val is_verified: Boolean,
    val issues_url: String,

    val login: String,
    val members_allowed_repository_creation_type: String,
    val members_can_create_internal_repositories: Boolean,
    val members_can_create_pages: Boolean,
    val members_can_create_private_repositories: Boolean,
    val members_can_create_public_repositories: Boolean,
    val members_can_create_repositories: Boolean,
    val members_url: String,
    val name: String,
    val node_id: String,
    val owned_private_repos: Int,
    val private_gists: Int,
    val public_gists: Int,
    val public_members_url: String,
    val repos_url: String,
    val two_factor_requirement_enabled: Boolean,
    val type: String,
    val updated_at: String
)