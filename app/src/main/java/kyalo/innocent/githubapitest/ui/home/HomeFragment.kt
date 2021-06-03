package kyalo.innocent.githubapitest.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kyalo.innocent.githubapitest.R
import kyalo.innocent.githubapitest.adapters.AllUsersAdapter
import kyalo.innocent.githubapitest.databinding.AllUsersFragmentBinding
import kyalo.innocent.githubapitest.models.github_users.AllUsersModel

class HomeFragment(): Fragment() {
    private lateinit var fUsersViewModel: HomeViewModel
    private lateinit var usersBinding: AllUsersFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        usersBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.all_users_fragment,
            container,
            false)

        fUsersViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        fUsersViewModel.usersList.observe(viewLifecycleOwner, { listOfUsers ->
            usersBinding.allUsersSourceList = listOfUsers
        })

        fUsersViewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError)
                onNetworkError()
        })

        setHasOptionsMenu(true)
        return usersBinding.root
    }

    private fun onNetworkError() {
        if (!fUsersViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            fUsersViewModel.onNetworkErrorShown()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView = item.actionView as SearchView?

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (fUsersViewModel.connected) {
                        val result = fUsersViewModel.searchUserRemotely(query)

                        val newList = mutableListOf<AllUsersModel>()
                        result?.let { newList.add(it) }
                        val myAdapter = activity?.let { AllUsersAdapter(newList, it.applicationContext) }
                        usersBinding.allUsersRecyler.adapter = myAdapter
                    }
                    else
                        searchDatabase(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // Here is where we are going to implement the filter logic
                query?.let { searchDatabase(it) }

                return true
            }
        })
        searchView?.setOnClickListener { }
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        fUsersViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, { usersQuery ->

            val myAdapter = activity?.applicationContext?.let { AllUsersAdapter(usersQuery, it) }
            usersBinding.allUsersRecyler.adapter = myAdapter
        })
    }
}