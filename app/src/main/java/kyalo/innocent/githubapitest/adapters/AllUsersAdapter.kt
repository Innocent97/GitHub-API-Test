package kyalo.innocent.githubapitest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import es.dmoral.toasty.Toasty
import kyalo.innocent.githubapitest.BR
import kyalo.innocent.githubapitest.R
import kyalo.innocent.githubapitest.databinding.AllUsersSingleItemBinding
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel
import kyalo.innocent.githubapitest.ui.home.HomeFragmentDirections

class AllUsersAdapter(private val allUsersList: List<AllUsersModel>?, private var context: Context) :
        RecyclerView.Adapter<AllUsersAdapter.UsersViewHolder>() {

    private var fOnItemClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val rootBinding: AllUsersSingleItemBinding = DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.all_users_single_item,
                        parent,
                        false)

        return UsersViewHolder(rootBinding.root)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val singleUser = allUsersList?.get(position)
        holder.binding?.setVariable(BR.singleUserItem, singleUser)
        holder.binding?.executePendingBindings()

        holder.itemView.setOnClickListener { currentView ->
            val actionToUserProfile = HomeFragmentDirections.actionToProfile()
            actionToUserProfile.gitHubUsername = singleUser?.login
            Navigation.findNavController(currentView).navigate(actionToUserProfile)
            //Toasty.info(context, "${singleUser?.login}", Toasty.LENGTH_LONG).show()
        }
        /*     val database = getAllUsersDatabase(context)
             val usersRepository = UsersRepository(database)
             val followers = singleUser?.login?.let { usersRepository.getNumberFollowers(it) }

             holder.binding?.tvUserFollowersNumber?.setText(followers?.value.toString())*/
    }

    override fun getItemCount(): Int {
        return allUsersList?.size!!
    }


    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: AllUsersSingleItemBinding? = DataBindingUtil.bind(itemView)

    }

}