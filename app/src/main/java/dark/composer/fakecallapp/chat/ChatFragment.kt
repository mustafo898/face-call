package dark.composer.fakecallapp.chat

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.R
import dark.composer.fakecallapp.chat.adapter.ChatAdapter
import dark.composer.fakecallapp.chat.adapter.ChatModel
import dark.composer.fakecallapp.chat.adapter.RewriteAdapter
import dark.composer.fakecallapp.databinding.FragmentChatBinding
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
            binding.isOnline.text = "typing..."

            lifecycleScope.launch {

                delay(2000)

                chatAdapter.add(ChatModel(it.message, false, it.receiveMessage))
                chatList.add(ChatModel(it.message, false, it.receiveMessage))
                binding.chat.scrollToPosition(chatList.lastIndex)
                binding.isOnline.text = "online"
            }
        }
    }

    override fun onBackPressed() {
        navController.navigate(R.id.homeFragment)
    }

    override fun onViewCreate() {
        rewriteList = ArrayList()
        chatList = ArrayList()
        binding.sms.adapter = rewriteAdapter
        binding.chat.adapter = chatAdapter

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        binding.rewrite.setOnClickListener {
            chatAdapter.delete()
        }

        binding.gift.setOnClickListener {
            navController.navigate(
                R.id.action_chatFragment_to_webViewFragment,
                bundleOf("url" to "https://creativecommons.org/publicdomain/zero/1.0/  ")
            )
        }

        rewriteList.add(ChatModel("Wow!", true, "Good!!"))
        rewriteList.add(ChatModel("Hi", true, "Hi Blonde"))
        rewriteList.add(ChatModel("Cool!", true, "Amazing!"))
        rewriteList.add(ChatModel("Wuh!", true, "Wooh!"))
        rewriteList.add(ChatModel("Good", true, "Cooooooll!"))
        rewriteList.add(ChatModel("How are you?", true, "18"))
        rewriteList.add(
            ChatModel(
                "What are your plans for the evening?",
                true,
                "In the evening I go with my friends to a cool party on a yacht"
            )
        )

        rewriteAdapter.set(rewriteList)

        binding.videoCall.setOnClickListener {
            navController.navigate(R.id.action_chatFragment_to_videoFragment)
        }
        binding.call.setOnClickListener {
            navController.navigate(R.id.action_chatFragment_to_callFragment)
        }
    }
}