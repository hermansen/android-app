package se.swosch.jackson.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import se.swosch.jackson.R
import se.swosch.jackson.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        MainFragmentBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.commands.observe(viewLifecycleOwner, Observer {
            when (it) {
                MainViewModel.Command.NavigateToListCommand -> navigateToList()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.commands.removeObservers(viewLifecycleOwner)
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_mainFragment_to_listFragment)
    }
}