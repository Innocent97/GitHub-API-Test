package kyalo.innocent.githubapitest.ui.repositories

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.dmoral.toasty.Toasty
import kyalo.innocent.githubapitest.R
import kyalo.innocent.githubapitest.adapters.AllUsersAdapter
import kyalo.innocent.githubapitest.adapters.OrganizationsAdapter
import kyalo.innocent.githubapitest.databinding.CollapsibleLayoutBinding
import kyalo.innocent.githubapitest.databinding.FragmentOrganizationRepositoriesBinding
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel


class OrganizationsFragment : Fragment() {
    private lateinit var fBinding: CollapsibleLayoutBinding
    //private lateinit var fViewModel: OrganizationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.collapsible_layout,
            container,
            false)

       /* fViewModel = ViewModelProvider(this).get(OrganizationsViewModel::class.java)

        // Observe the list of orgs
        fViewModel.fOrganizations.observe(viewLifecycleOwner, Observer { orgsList ->
            fBinding.recyclerDataSource = orgsList
        })

        // Observe the network error boolean
        fViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isNetworkError ->
            if (isNetworkError)
                onNetworkError()
        })

        setHasOptionsMenu(true)*/

        return fBinding.root
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        inflater.inflate(R.menu.search_organizations_menu_layout, menu)
        val item = menu.findItem(R.id.action_search_organization)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView = item.actionView as SearchView?

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (fViewModel.connected) {
                        val result = fViewModel.queryOrganizationRemotely(query)

                        if (result != null) {
                            val newList = mutableListOf<OrgModel>()
                            result?.let { newList.add(it) }
                            val myAdapter = activity?.let { OrganizationsAdapter(newList) }
                            fBinding.organizationsRecycler.adapter = myAdapter
                        } else
                            Toasty.info(requireContext(), "Organization doesn't exist", Toasty.LENGTH_LONG).show()


                    }
                    else
                        searchOrganizationRemotely(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // Here is where we are going to implement the filter logic
                query?.let { searchOrganizationRemotely(it) }

                return true
            }
        })
        searchView?.setOnClickListener { }
    }

    // Query server for data
    private fun searchOrganizationRemotely(query: String) {
        //fViewModel.queryOrganizationRemotely(query)

        val searchQuery = "%$query%"

        fViewModel.getOrgLocally(searchQuery).observe(viewLifecycleOwner, { orgQuery ->

            if (orgQuery != null) {
                val myAdapter = activity?.applicationContext?.let { OrganizationsAdapter(orgQuery) }
                fBinding.organizationsRecycler.adapter = myAdapter
            } else
                Toasty.info(requireContext(), "Organization doesn't exist", Toasty.LENGTH_LONG).show()

        })
    }

    private fun onNetworkError() {
        if (!fViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            fViewModel.onNetworkErrorShown()
        }
    }*/
}