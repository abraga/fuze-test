package com.github.abraga.fuzetest.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.abraga.fuzetest.data.models.MatchResponse
import com.github.abraga.fuzetest.data.models.State
import com.github.abraga.fuzetest.databinding.ActivityMainBinding
import com.github.abraga.fuzetest.presentation.MatchesViewModel
import com.github.abraga.fuzetest.presentation.details.DetailsActivity
import com.github.abraga.fuzetest.presentation.details.DetailsActivity.Companion.SELECTED_MATCH_KEY
import com.github.abraga.fuzetest.presentation.main.adapter.MatchesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MatchesViewModel by viewModels()
    private val matchesAdapter = MatchesAdapter(::onClickMatch)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buildRecyclerView()
        observeMatches()
        getMatchesList()
        handleWarning()
    }

    private fun buildRecyclerView() {
        binding.rvMatchesList.adapter = matchesAdapter
    }

    private fun getMatchesList() {
        viewModel.getMatches()
    }

    private fun observeMatches() = with(viewModel) {
        lifecycleScope.launchWhenCreated {
            matchesFlow.collectLatest {
                when (it.status) {
                    State.SUCCESS -> {
                        showContent()
                        it.data?.let { data ->
                            matchesAdapter.submitData(data)
                        }
                    }
                    State.ERROR -> showError()
                    State.LOADING -> showLoading()
                }
            }
        }
    }

    private fun onClickMatch(match: MatchResponse) {
        Intent(this, DetailsActivity::class.java).run {
            putExtra(SELECTED_MATCH_KEY, match)
            startActivity(this)
        }
    }

    private fun handleWarning() = with(binding.warning) {
        btWarningTryAgain.setOnClickListener {
            getMatchesList()
        }
    }

    private fun showContent() {
        binding.vfFlipper.displayedChild = 0
    }

    private fun showLoading() {
        binding.vfFlipper.displayedChild = 1
    }

    private fun showError() {
        binding.vfFlipper.displayedChild = 2
    }
}