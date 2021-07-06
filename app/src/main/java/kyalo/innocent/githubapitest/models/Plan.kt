package kyalo.innocent.githubapitest.models

data class Plan(
    val filled_seats: Int,
    val name: String,
    val private_repos: Int,
    val seats: Int,
    val space: Int
)