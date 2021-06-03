package kyalo.innocent.githubapitest.binding_adatpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kyalo.innocent.githubapitest.adapters.AllUsersAdapter
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel

@BindingAdapter("allUsersAdapter")
fun setAllUsersAdapter(recyclerView: RecyclerView?, usersList: List<AllUsersModel>?) {
    // check if the list is null
    if (usersList == null)
        return

    // check if the layout manager is null
    val layoutManager: RecyclerView.LayoutManager? = recyclerView?.layoutManager
    if (layoutManager == null) {
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    // check if adapter is null
    var reposAdapter: AllUsersAdapter? = recyclerView?.adapter as AllUsersAdapter?
    if (reposAdapter == null) {
        reposAdapter = recyclerView?.context?.let { AllUsersAdapter(usersList, recyclerView.context) }
        recyclerView?.adapter = reposAdapter
    }

}

@BindingAdapter("allUsersImage")
fun setUpUserImage(userImage: ImageView?, imageUrl: String?) {
    userImage?.let {
        Glide.with(userImage.getContext())
            .load(imageUrl).apply(RequestOptions().circleCrop())
            .into(it)
    }
}