package dark.composer.fakecallapp.contacts

import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.contacts.adapter.ContactModel
import dark.composer.fakecallapp.contacts.adapter.ContactsAdapter
import dark.composer.fakecallapp.contacts.dialog.ADDialog
import dark.composer.fakecallapp.databinding.FragmentContactsBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private val contactAdapter by lazy {
        ContactsAdapter()
    }


    override fun onViewCreate() {
        binding.rv.adapter = contactAdapter

        val sharedPref = EncryptedSharedPref(requireContext())

        contactAdapter.setItemClickListener { isOpen, count, pos ->
            if (!isOpen) {
                val dialog = ADDialog(requireContext()) { c1, p1 ->
                    contactAdapter.update(c1, p1)
                    showAd()
                }

                dialog.getCount(count, pos)
                dialog.show()
            }
        }

        binding.back.setOnClickListener {
            navController.navigate(R.id.action_contactsFragment_to_homeFragment)
        }

//        val list = mutableListOf<ContactModel>()
//        list.add(ContactModel("Character 1", "+1248754248", 0, true, false))
//        list.add(ContactModel("Character 2", "+1248754248", 0, true, false))
//        list.add(ContactModel("Character 3", "+1248754248", 0, false, false))
//        list.add(ContactModel("Character 4", "+1248754248", 0, false, false))
//        list.add(ContactModel("Character 5", "+1248754248", 0, false, false))
//        list.add(ContactModel("Character 6", "+1248754248", 0, false, false))

        contactAdapter.set(sharedPref.getList())
    }
}