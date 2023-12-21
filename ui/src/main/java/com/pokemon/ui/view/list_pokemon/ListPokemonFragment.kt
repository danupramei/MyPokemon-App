package com.pokemon.ui.view.list_pokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.kimiafarma.marvee.feature_onboarding.ui.fragments.splash.ListPokemonViewModel
import com.pokemon.shared.base.BaseFragment
import com.pokemon.shared.constant.Const
import com.pokemon.shared.extension.gone
import com.pokemon.shared.extension.invisible
import com.pokemon.shared.extension.launchWhenResumed
import com.pokemon.shared.extension.navigate
import com.pokemon.shared.extension.showToastMessage
import com.pokemon.shared.extension.verticalGridLayoutManager
import com.pokemon.shared.extension.visible
import com.pokemon.shared.interfaces.ItemListClickListener
import com.pokemon.shared.ui_models.UiPokemon
import com.pokemon.ui.R
import com.pokemon.ui.adapter.PokemonAdapter
import com.pokemon.ui.adapter.footer.PagerFooterAdapter
import com.pokemon.ui.databinding.FragmentListPokemonBinding
import com.pokemon.ui.enums.RouteEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ListPokemonFragment : BaseFragment<FragmentListPokemonBinding>(FragmentListPokemonBinding::inflate) {

    @Inject
    lateinit var listAdapter: PokemonAdapter

    private val viewModel by viewModels<ListPokemonViewModel>()

    private var isFirstTime = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePokemonList()
        initView()
        initAdapter()
    }

    private fun initView() {
        if (isFirstTime){
            binding.swipeListPokemon.isRefreshing = true
            viewModel.getListPokemon()
            isFirstTime = false
        }
        binding.apply {
            toolbarPokemonList.imgBack.invisible()
            toolbarPokemonList.toolbarMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_my_pokemon -> {
                        navigateToMyPokemon()
                        return@setOnMenuItemClickListener false
                    }
                    else -> false
                }
            }
            swipeListPokemon.setOnRefreshListener {
                viewModel.getListPokemon()
            }
        }
    }

    private fun initAdapter() {
        listAdapter.apply {
            addLoadStateListener { loadState ->
                try {
                    when (val currentState = loadState.source.refresh) {
                        is LoadState.Loading -> {
                            binding.swipeListPokemon.isRefreshing = true
                        }

                        is LoadState.NotLoading -> {
                            binding.swipeListPokemon.isRefreshing = false
                            if (loadState.append.endOfPaginationReached && itemCount < 1) {
                                binding.rcListPokemon.gone()
                                return@addLoadStateListener
                            }
                            binding.rcListPokemon.visible()
                        }

                        is LoadState.Error -> {
                            binding.swipeListPokemon.isRefreshing = false
                            binding.rcListPokemon.gone()
                            val extractedException = currentState.error
                            extractedException.localizedMessage?.let { showToastMessage(it) }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            withLoadStateFooter(
                footer = PagerFooterAdapter(
                    onRetryClick = {
                        listAdapter.retry()
                    }
                )
            )

            setItemClickListener(object : ItemListClickListener<UiPokemon?> {
                override fun onItemClickListener(data: UiPokemon?, position: Int) {
                    data?.id?.let {
                        navigateToDetail(it)
                    }
                }
            })
        }

        binding.rcListPokemon.apply {
            adapter = listAdapter
            isNestedScrollingEnabled = true
            val verticalLayout = verticalGridLayoutManager(2)
            layoutManager = verticalLayout
        }
    }

    private fun navigateToDetail(id: Int) {
        navigate(
            route = RouteEnum.DETAIL_POKEMON.getRouteName(this),
            extras = mapOf(Const.POKEMON_ID to id)
        )
    }

    private fun navigateToMyPokemon() {
        navigate(route = RouteEnum.MY_POKEMON.getRouteName(this))
    }

    private fun observePokemonList() {
        launchWhenResumed {
            viewModel.listPokemon.collectLatest {
                it?.let { data ->
                    listAdapter.submitData(lifecycle, data)
                }
            }
        }
    }
}