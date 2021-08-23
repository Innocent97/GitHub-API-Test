package kyalo.innocent.githubapitest.database.user_repos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "License")
data class License(
    @PrimaryKey
    val key: String?,
    val name: String?,
    val node_id: String?,
    val spdx_id: String?,
    val url: String?
)