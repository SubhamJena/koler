package com.chooloo.www.koler.ui.contacts

import com.chooloo.www.koler.R
import com.chooloo.www.koler.adapter.ContactsAdapter
import com.chooloo.www.koler.data.account.ContactAccount
import com.chooloo.www.koler.ui.list.ListController

open class ContactsController<V : ContactsContract.View>(view: V) :
    ListController<ContactAccount, V>(view),
    ContactsContract.Controller<V> {

    override val adapter by lazy { ContactsAdapter(component) }
    override val noResultsIconRes = R.drawable.round_people_24
    override val noResultsTextRes = R.string.error_no_results_contacts
    override val noPermissionsTextRes = R.string.error_no_permissions_contacts


    private val contactsLiveData by lazy {
        component.liveDataFactory.allocContactsProviderLiveData()
    }

    override fun applyFilter(filter: String) {
        try {
            contactsLiveData.filter = filter
        } catch (e: Exception) {
        }
    }

    override fun onItemClick(item: ContactAccount) {
        view.openContact(item)
    }

    override fun fetchData(callback: (List<ContactAccount>, hasPermissions: Boolean) -> Unit) {
        component.permissions.runWithReadContactsPermissions {
            if (it) {
                contactsLiveData.observe(component.lifecycleOwner) { data ->
                    callback.invoke(data, true)
                }
            } else {
                callback.invoke(emptyList(), false)
            }
        }
    }
}