package kyalo.innocent.githubapitest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kyalo.innocent.githubapitest.BR
import kyalo.innocent.githubapitest.R
import kyalo.innocent.githubapitest.databinding.OrganizationReposLayoutItemBinding
import kyalo.innocent.githubapitest.models.OrgModel
import kyalo.innocent.githubapitest.models.OrganizationModel

class OrganizationsAdapter(private val organizationsList: List<OrgModel>):
    RecyclerView.Adapter<OrganizationsAdapter.OrganizationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationViewHolder {
        val rootBinding: OrganizationReposLayoutItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.organization_repos_layout_item,
            parent,
            false
        )

        return OrganizationViewHolder(rootBinding.root)
    }

    override fun onBindViewHolder(holder: OrganizationViewHolder, position: Int) {
        val orgData = organizationsList.get(position)
        holder.binding?.setVariable(BR.organizationInformation, orgData)
        holder.binding?.executePendingBindings()
    }

    override fun getItemCount(): Int = organizationsList.size


    class OrganizationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: OrganizationReposLayoutItemBinding? = DataBindingUtil.bind(itemView)
    }
}