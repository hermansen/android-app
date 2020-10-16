package se.swosch.jackson.ui.main

import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.main_fragment.*
import se.swosch.jackson.R
import se.swosch.jackson.databinding.MainFragmentBinding
import kotlin.math.roundToInt

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
        viewModel.uiState.observe(viewLifecycleOwner, Observer {
            when (it) {
                MainViewModel.UIState.Done -> swipeRefresh.isRefreshing = false
                MainViewModel.UIState.Refreshing -> swipeRefresh.isRefreshing = true

            }
        })
        swipeRefresh.setOnRefreshListener(viewModel)
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            viewModel.updateRating(rating.roundToInt())
        }
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_mainFragment_to_listFragment)
    }
}