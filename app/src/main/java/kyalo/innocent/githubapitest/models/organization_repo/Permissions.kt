package kyalo.innocent.githubapitest.models.organization_repo

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)