package com.danilovfa.space.presentation.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentDialogTextBinding
import com.danilovfa.space.utils.extensions.TAG
import com.danilovfa.space.utils.extensions.addGradient

class TextDialogFragment(
    private val title: String,
    private val body: String,
    private val onDismissCallback: (() -> Unit)? = null
) : BaseDialogFragment<FragmentDialogTextBinding>(FragmentDialogTextBinding::inflate) {
    companion object {
        fun display(
            fragmentManager: FragmentManager,
            title: String,
            body: String,
            onDismissCallback: (() -> Unit)? = null
        ): TextDialogFragment {
            val dialog = TextDialogFragment(title, body, onDismissCallback)
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
            titleTextView.text = title
            bodyTextView.text = body
            okTextView.addGradient(requireContext())
            okTextView.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissCallback?.invoke()
    }
}