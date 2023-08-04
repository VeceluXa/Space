package com.danilovfa.space.presentation.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentDialogTypeBinding
import com.danilovfa.space.utils.extensions.TAG
import com.danilovfa.space.utils.extensions.addGradient

class TypeDialogFragment(
    private val hint: String? = null,
    private val onPositiveButtonCallback: (text: String) -> Unit
) : BaseDialogFragment<FragmentDialogTypeBinding>(FragmentDialogTypeBinding::inflate) {
    companion object {
        fun display(
            fragmentManager: FragmentManager,
            hint: String? = null,
            onPositiveButtonCallback: (text: String) -> Unit
        ): TypeDialogFragment {
            val dialog = TypeDialogFragment(hint, onPositiveButtonCallback)
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Space_Dialog_Basic)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            editText.hint = hint

            cancelTextView.apply {
                addGradient(requireContext())
                setOnClickListener { dismiss() }
            }

            saveTextView.apply {
                addGradient(requireContext())
                setOnClickListener {
                    onPositiveButtonCallback(editText.text.toString())
                    dismiss()
                }
            }
        }
    }
}