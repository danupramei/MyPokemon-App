package com.pokemon.ui.view.my_pokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.pokemon.domain.commons.Resource
import com.pokemon.shared.base.BaseFragment
import com.pokemon.shared.constant.Const
import com.pokemon.shared.extension.launchWhenResumed
import com.pokemon.shared.extension.navigate
import com.pokemon.shared.extension.onBackPressed
import com.pokemon.shared.extension.setSafeOnClickListener
import com.pokemon.shared.extension.showToastMessage
import com.pokemon.shared.extension.verticalGridLayoutManager
import com.pokemon.shared.interfaces.ItemListClickListener
import com.pokemon.shared.ui_models.UiPokemon
import com.pokemon.ui.adapter.PokemonAdapter
import com.pokemon.ui.databinding.FragmentMyPokemonBinding
import com.pokemon.ui.enums.RouteEnum
import com.pokemon.ui.view.input_nick_dialog.InputNicknameDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MyPokemonFragment : BaseFragment<FragmentMyPokemonBinding>(FragmentMyPokemonBinding::inflate) {

    @Inject
    lateinit var listAdapter: PokemonAdapter

    private val viewModel by viewModels<MyPokemonViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMyPokemon()
        observeError()
        initView()
        initAdapter()
    }

    private fun initView() {
        viewModel.getMyPokemon()
        binding.apply {
            toolbarPokemonList.imgBack.setSafeOnClickListener(lifecycle) {
                onBackPressed()
            }
            swipeMyPokemon.setOnRefreshListener {
                viewModel.getMyPokemon()
            }
        }
    }

    private fun initAdapter() {
        listAdapter.apply {
            setItemClickListener(object : ItemListClickListener<UiPokemon?> {
                override fun onItemClickListener(data: UiPokemon?, position: Int) {
                    data?.id?.let {
                        navigateToDetail(it)
                    }
                }
            })
            setOnReleaseItemButtonClicked {
                viewModel.releasePokemon(it)
            }
            setOnRenameIconClicked {
                it.id?.let { pokemonId ->
                    showInputNicknameDialog(pokemonId, it.nick)
                }
            }
        }

        binding.rcMyPokemon.apply {
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

    private fun showInputNicknameDialog(pokemonId: Int, nickname: String?) {
        activity?.let { ctx ->
            val dialog = InputNicknameDialog(ctx, nickname) { nickname ->
                viewModel.renamePokemon(pokemonId, nickname)
            }
            dialog.show()
        }
    }

    private fun observeMyPokemon() {
        launchWhenResumed {
            viewModel.listMyPokemon.collectLatest {
                when(it) {
                    is Resource.Loading -> binding.swipeMyPokemon.isRefreshing = true
                    is Resource.Success -> {
                        binding.swipeMyPokemon.isRefreshing = false
                        it.data?.let { data ->
                            listAdapter.submitData(PagingData.from(data))
                        }
                    }
                    is Resource.Error -> {
                        binding.swipeMyPokemon.isRefreshing = false
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun observeError() {
        launchWhenResumed {
            viewModel.error.collectLatest {
                it?.let { msg ->
                    showToastMessage(msg)
                }
            }
        }
    }
}