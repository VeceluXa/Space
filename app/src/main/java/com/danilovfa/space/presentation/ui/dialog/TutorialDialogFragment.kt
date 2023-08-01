package com.danilovfa.space.presentation.ui.dialog

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentDialogDetailsTutorialBinding
import com.danilovfa.space.utils.extensions.TAG

class TutorialDialogFragment :
    BaseDialogFragment<FragmentDialogDetailsTutorialBinding>(FragmentDialogDetailsTutorialBinding::inflate) {

    companion object {
        fun display(fragmentManager: FragmentManager): TutorialDialogFragment {
            val dialog = TutorialDialogFragment()
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window?.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Space_Dialog_Fullscreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            dismiss()
        }
    }
}