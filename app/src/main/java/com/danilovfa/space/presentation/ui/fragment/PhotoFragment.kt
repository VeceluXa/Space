package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import com.danilovfa.space.databinding.FragmentPhotoBinding
import com.danilovfa.space.utils.BackButtonListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding>(FragmentPhotoBinding::inflate),
    BackButtonListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView3.text = requireArguments().getString(EXTRA_IMAGE_URL)
    }
    override fun onBackPressed(): Boolean {
        return true
    }

    companion object {
        private const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"
        fun getNewInstance(imageUrl: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_IMAGE_URL, imageUrl)
                }
            }
    }
}