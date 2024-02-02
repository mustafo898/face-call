package dark.composer.fakecallapp.chat

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.chat.adapter.ChatAdapter
import dark.composer.fakecallapp.chat.adapter.ChatModel
import dark.composer.fakecallapp.chat.adapter.RewriteAdapter
import dark.composer.fakecallapp.databinding.FragmentChatBinding
import dark.composer.fakecallapp.utl.EncryptedSharedPref
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n")
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private lateinit var rewriteList: ArrayList<ChatModel>
    private lateinit var chatList: ArrayList<ChatModel>

    private val chatAdapter by lazy {
        ChatAdapter {

        }
    }

    private val rewriteAdapter by lazy {
        RewriteAdapter {
            chatAdapter.add(ChatModel(it.message, true, it.receiveMessage))
            chatList.add(ChatModel(it.message, true, it.receiveMessage))
            binding.chat.smoothScrollToPosition(chatList.lastIndex)
            binding.isOnline.text = "typing..."

            lifecycleScope.launch {

                delay((500..1500).random().toLong())

                chatAdapter.add(ChatModel(it.message, false, it.receiveMessage))
                chatList.add(ChatModel(it.message, false, it.receiveMessage))
                binding.chat.smoothScrollToPosition(chatList.lastIndex)
                binding.isOnline.text = "online"
            }
        }
    }

    override fun onBackPressed() {

    }

    override fun onViewCreate() {
        val sharedPref = EncryptedSharedPref(requireContext())
        rewriteList = ArrayList()
        chatList = ArrayList()
        binding.sms.adapter = rewriteAdapter
        binding.chat.adapter = chatAdapter

        sharedPref.getList().forEach {
            if (it.selected) {
                binding.image.setImageResource(it.image)
                binding.name.text = it.name
            }
        }

        binding.back.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        binding.rewrite.setOnClickListener {
            chatAdapter.delete()
        }

        binding.gift.setOnClickListener {
            game()
        }

        var name = ""
        sharedPref.getList().forEach {
            if (it.selected) name = it.name
        }

        rewriteList.add(ChatModel("Hi", true, "Hi! Welcome to my chat"))
        rewriteList.add(ChatModel("What's your name", true, "I'm $name"))
        rewriteList.add(ChatModel("How are you?", true, "I'm fine, thank you"))
        rewriteList.add(ChatModel("How old are you?", true, "I'm 25 years old"))
        rewriteList.add(ChatModel("Nice to meet you", true, "Me too"))
        rewriteList.add(ChatModel("Where are you from?", true, "I'm from United States"))
        rewriteList.add(ChatModel("What kind of food do you like?", true, "I love pizza!"))
        rewriteList.add(ChatModel("Can I call you now?", true, "Yes! I'm waiting for your call!!!"))
        rewriteList.add(ChatModel("Can we have a video call with you now?", true, "Yes, of course"))
        rewriteList.add(
            ChatModel(
                "What is you favourite cartoon?", true, "My favourite cartoon is Lion King"
            )
        )
        rewriteList.add(ChatModel("What is your education?", true, "I'm in high school in London"))
        rewriteList.add(
            ChatModel(
                "Is it possible to be friends?", true, "Yes, of course. Let's become friends"
            )
        )
        rewriteList.add(ChatModel("Goodbye! See you!", true, "See you again"))
        rewriteList.add(ChatModel("Have a nice day!", true, "Thank you for this chat, you too!"))

        rewriteAdapter.set(rewriteList)

        binding.videoCall.setOnClickListener {
            navController.navigate(R.id.action_chatFragment_to_videoFragment)
        }
        binding.call.setOnClickListener {
            navController.navigate(R.id.action_chatFragment_to_callFragment)
        }
    }

}