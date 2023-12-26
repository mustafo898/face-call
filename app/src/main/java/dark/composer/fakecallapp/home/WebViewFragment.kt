package dark.composer.fakecallapp.home

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import dark.composer.fakecallapp.BaseFragment
import dark.composer.fakecallapp.databinding.FragmentWebViewBinding

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreate() {
        var url = ""

        binding.back.setOnClickListener {
            navController.popBackStack()
        }

        arguments?.let {
            url = it.getString("url", "")
        }

        binding.webview.settings.javaScriptEnabled = true

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        binding.webview.loadUrl(url)
    }
}