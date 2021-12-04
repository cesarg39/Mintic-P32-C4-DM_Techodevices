package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.AddCommentFragmentBinding
import com.misiontic2022.technodevices.model.remote.CommentDataSource
import com.misiontic2022.technodevices.viewModel.domain.comment.CommentRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.comment.CommentViewModel
import com.misiontic2022.technodevices.viewModel.presentation.comment.CommentViewModelFactory

class AddCommentFragment : Fragment(R.layout.add_comment_fragment) {

    private lateinit var binding: AddCommentFragmentBinding
    private val args: AddCommentFragmentArgs by navArgs()
    private val viewModel by viewModels<CommentViewModel> {
        CommentViewModelFactory(CommentRepoImpl(CommentDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddCommentFragmentBinding.bind(view)

        binding.btnAddComments.setOnClickListener {
            addComment()
        }
    }

    private fun addComment() {
        val description = binding.etComment.text.toString()

        viewModel.addComment(description, args.id)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.show()
                    }
                    is Result.Success -> {
                        findNavController().navigateUp()
                        binding.progressBar.hide()
                    }
                    is Result.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            "Error: ${it.exception}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }

}