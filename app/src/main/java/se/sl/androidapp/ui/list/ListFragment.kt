package se.sl.androidapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import se.sl.androidapp.databinding.FragmentListBinding
import se.sl.androidapp.ui.ChuckJoke

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentListBinding.inflate(inflater, container, false).also {
        it.lifecycleOwner = viewLifecycleOwner
        it.viewModel = viewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jokeList.apply {
            adapter = JokeListAdapter()
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(DividerItemDecoration(view.context, LinearLayout.VERTICAL))
        }

        viewModel.commands.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ListViewModel.Command.NavigateToDetails -> navigateToDetails(it.joke)
            }
        })
    }

    private fun navigateToDetails(joke: ChuckJoke) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToDetailFragment(joke)
        )
    }
}