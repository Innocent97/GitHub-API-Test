package kyalo.innocent.githubapitest.models.repos_models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RepositoryItem constructor(
        @PrimaryKey
        @NonNull
        val url: String,

        @SerializedName("archive_url")
        val archiveUrl: String?,

        @SerializedName("branches_url")
        val branchesUrl: String?,

        @SerializedName("clone_url")
        val cloneUrl: String?,

        @SerializedName("created_at")
        val createdAt: String?,

        val description: String?,

        @SerializedName("events_url")
        val eventsUrl: String?,

        @SerializedName("forks_count")
        val forksCount: Int?,

        val gitUrl: String?,

        val id: Int?,

        val language: String?,
        @SerializedName("languages_url")
        val languagesUrl: String?,

        val name: String?,

        //val owner: Owner?,

        val updatedAt: String?,

        val watchers: Int?,

        @SerializedName("watchers_count")
        val watchersCount: Int?
)