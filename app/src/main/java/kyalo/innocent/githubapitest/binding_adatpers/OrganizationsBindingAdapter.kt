package kyalo.innocent.githubapitest.binding_adatpers

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kyalo.innocent.githubapitest.adapters.OrganizationsAdapter
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel

@BindingAdapter("organizationsAdapter")
fun setOrganizationsAdapter(recyclerView: RecyclerView?, orgsList: List<OrgModel>?) {

    if (orgsList == null)
        return

    val layoutManager: RecyclerView.LayoutManager? = recyclerView?.layoutManager
    if (layoutManager == null) {
        if (recyclerView != null)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
    }

    // check if adapter is null
    var orgsAdapter: OrganizationsAdapter? = recyclerView?.adapter as OrganizationsAdapter?
    if (orgsAdapter == null) {
        orgsAdapter = recyclerView?.context.let { context -> OrganizationsAdapter(orgsList) }
        recyclerView?.adapter = orgsAdapter
    }
}

@BindingAdapter("orgsImage")
fun setUpOrganizationsImage(userImage: ImageView?, imageUrl: String?) {
    userImage?.let {
        Glide.with(userImage.getContext())
            .load(imageUrl).apply(RequestOptions().circleCrop())
            .into(it)
    }
}

// Set followers text
@BindingAdapter("setFollowersText")
fun setFollowersText(textView: TextView, followersString: String) {
    val followersText = "followers"
    textView.let {
        it.text = "$followersString $followersText"
    }
}

// Set following text
@BindingAdapter("setFollowingText")
fun setFollowingText(textView: TextView, followersString: String) {
    val followersText = "following"
    textView.let {
        it.text = "$followersString $followersText"
    }
}