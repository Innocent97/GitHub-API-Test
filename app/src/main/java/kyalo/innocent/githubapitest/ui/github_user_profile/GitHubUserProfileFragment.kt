package kyalo.innocent.githubapitest.ui.github_user_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import es.dmoral.toasty.Toasty
import kyalo.innocent.githubapitest.R
import kyalo.innocent.githubapitest.databinding.FragmentGithubUserProfileBinding
import kyalo.innocent.githubapitest.models.github_users.GitHubUserModel

class GitHubUserProfileFragment: Fragment() {

    private lateinit var userProfileBinding: FragmentGithubUserProfileBinding
    private lateinit var userProfileViewModel: GitHubUserViewModel
    private val args: GitHubUserProfileFragmentArgs by navArgs()
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = args.gitHubUsername.toString()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        username = args.gitHubUsername.toString()

        userProfileBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_github_user_profile,
            container,
            false)

        userProfileViewModel = ViewModelProvider(this).get(GitHubUserViewModel::class.java)
        username?.let { userProfileViewModel.setUsername(it) }

        userProfileViewModel.username.value?.let { userProfileViewModel.getUserData(it) }

       /* userProfileViewModel.username.value?.let { userProfileViewModel.searchUserInDB(it) }

        Toasty.info(requireContext(), "${userProfileViewModel.username.value}", Toasty.LENGTH_SHORT).show()

        // Observe searched user property
        userProfileViewModel.gitHubUser?.observe(viewLifecycleOwner,) { remoteUser ->
            userProfileBinding.gitHubUser = remoteUser
        }*/

        userProfileViewModel.username.value?.let {
            userProfileViewModel.searchUserInDB(it)?.observe(viewLifecycleOwner, { user ->
                userProfileBinding.gitHubUser = user
                if (user != null)
                    Toasty.info(requireContext(), "${user.login} ${user.email}", Toasty.LENGTH_LONG).show()
            })
        }

        userProfileViewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError)
                onNetworkError()
        })

        return userProfileBinding.root
    }

    private fun onNetworkError() {
        if (userProfileViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            userProfileViewModel.onNetworkErrorShown()
        }
    }
}