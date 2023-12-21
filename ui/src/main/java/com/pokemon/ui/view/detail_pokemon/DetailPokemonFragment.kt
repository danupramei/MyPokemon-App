package com.pokemon.ui.view.detail_pokemon

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.google.android.material.appbar.AppBarLayout
import com.kimiafarma.marvee.feature_onboarding.ui.fragments.splash.DetailPokemonViewModel
import com.pokemon.domain.commons.Resource
import com.pokemon.shared.base.BaseFragment
import com.pokemon.shared.constant.Const
import com.pokemon.shared.custom_view.LoadingDialog
import com.pokemon.shared.extension.getDataFromPreviousFragment
import com.pokemon.shared.extension.gone
import com.pokemon.shared.extension.launchWhenResumed
import com.pokemon.shared.extension.loadNetworkImage
import com.pokemon.shared.extension.onBackPressed
import com.pokemon.shared.extension.setSafeOnClickListener
import com.pokemon.shared.extension.showToastMessage
import com.pokemon.shared.extension.visible
import com.pokemon.shared.ui_models.UiPokemon
import com.pokemon.shared.ui_models.UiStat
import com.pokemon.shared.utils.AppBarStateChangeListener
import com.pokemon.shared.utils.MyPokemonUtil.imageUrlToBitmap
import com.pokemon.shared.utils.MyPokemonUtil.isCatched
import com.pokemon.ui.R
import com.pokemon.ui.databinding.FragmentDetailPokemonBinding
import com.pokemon.ui.databinding.ItemMovesPokemonBinding
import com.pokemon.ui.databinding.ItemStatsPokemonBinding
import com.pokemon.ui.view.input_nick_dialog.InputNicknameDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailPokemonFragment : BaseFragment<FragmentDetailPokemonBinding>(FragmentDetailPokemonBinding::inflate) {

    private val viewModel by viewModels<DetailPokemonViewModel>()
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog.getInstance(context) }

    private var pokemonName: String? = null
    private var idPokemon: Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCollapseLayout()
        observeDetailPokemon()
        observeIsMyPokemon()
        observeCatchPokemon()
        initRequest()
    }

    private fun initRequest() {
        getDataFromPreviousFragment<Int>(Const.POKEMON_ID)?.let { id ->
            idPokemon = id
            viewModel.getPokemon(idPokemon)
        }
    }

    private fun initCollapseLayout() {
        binding.apply {
            toolbar.setElevation(0)
            toolbar.imgBack.setSafeOnClickListener(lifecycle) {
                onBackPressed()
            }
            collapsingToolbar.setCollapsedTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.pokemon.shared.R.color.black
                )
            )
            collapsingToolbar.setExpandedTitleColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.pokemon.shared.R.color.transparent
                )
            )

            collapseApp.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    when (state) {
                        State.COLLAPSED -> {
                            toolbar.title.setTextColor(ContextCompat.getColor(requireContext(), com.pokemon.shared.R.color.black))
                            toolbar.title.text = pokemonName
                            toolbar.imgBack.setColorFilter(ContextCompat.getColor(requireContext(), com.pokemon.shared.R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
                        }
                        State.EXPANDED -> {
                            toolbar.title.setTextColor(ContextCompat.getColor(requireContext(), com.pokemon.shared.R.color.transparent))
                            toolbar.title.text = ""
                            toolbar.imgBack.setColorFilter(ContextCompat.getColor(requireContext(), com.pokemon.shared.R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
                        }
                        else -> {
                            toolbar.title.setTextColor(ContextCompat.getColor(requireContext(), com.pokemon.shared.R.color.transparent))
                        }
                    }
                }
            })
        }
    }

    private fun setView(data: UiPokemon) {
        binding.apply {
            data.id?.let { viewModel.checkIsMyPokemon(it) }
            pokemonName = data.name
            tvPokemonName.text = pokemonName
            tvType.text = data.type
            tvWeight.text = data.weight
            tvHeight.text = data.height
            setBackgroundColor(data.imgUrl)
            imgPokemon.loadNetworkImage(data.imgUrl, R.drawable.img_pokeball)
            addViewStats(data.stats)
            addViewMoves(data.moves)
            btnOrderNow.setSafeOnClickListener(lifecycle) {
                if (isCatched()) showInputNicknameDialog(data)
                else showToastMessage("Gagal menangkap ${pokemonName}")
            }
        }
    }

    private fun showInputNicknameDialog(pokemon: UiPokemon) {
        activity?.let { ctx ->
            val dialog = InputNicknameDialog(ctx) { nickname ->
                viewModel.catchPokemon(pokemon, nickname)
            }
            dialog.show()
        }
    }

    private fun addViewStats(stats: List<UiStat?>?) {
        binding.apply {
            stats?.forEach {
                val itemStatBinding = ItemStatsPokemonBinding.inflate(layoutInflater)
                itemStatBinding.tvStatName.text = it?.name
                itemStatBinding.tvStatValue.text = it?.base_stat.toString()
                llStats.addView(itemStatBinding.root)
            }
        }
    }

    private fun addViewMoves(moves: List<String?>?) {
        binding.apply {
            moves?.forEach {
                val itemMoveBinding = ItemMovesPokemonBinding.inflate(layoutInflater)
                itemMoveBinding.tvMoveName.text = it
                llMoves.addView(itemMoveBinding.root)
            }
        }
    }

    private fun observeDetailPokemon() {
        launchWhenResumed {
            viewModel.pokemonState.collectLatest {
                when(it) {
                    is Resource.Loading -> loadingDialog.show()
                    is Resource.Success -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            setView(data)
                        }
                    }
                    is Resource.Error -> {
                        loadingDialog.dismiss()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setBackgroundColor(imgUrl: String?) {
        imgUrl?.let { imageUrl ->
            lifecycleScope.launch(Dispatchers.IO) {
                activity?.let { ctx ->
                    val bitmap = async {
                        imageUrlToBitmap(ctx, imageUrl)
                    }.await()
                    bitmap?.let {
                        Palette.from(bitmap).generate { palette ->
                            val dominantColor = palette?.dominantSwatch?.rgb ?: 0
                            binding.imgPokemon.setBackgroundColor(dominantColor)
                        }

                    }
                }
            }
        }
    }

    private fun observeIsMyPokemon() {
        launchWhenResumed {
            viewModel.isMyPokemon.collectLatest {
                binding.apply {
                    if (it) btnOrderNow.gone() else btnOrderNow.visible()
                }
            }
        }
    }

    private fun observeCatchPokemon() {
        launchWhenResumed {
            viewModel.isAdded.collectLatest {
                when (it) {
                    is Resource.Loading -> loadingDialog.show()
                    is Resource.Success -> {
                        loadingDialog.dismiss()
                        binding.btnOrderNow.gone()
                        showToastMessage("Berhasil menangkap $pokemonName")
                    }
                    is Resource.Error -> {
                        loadingDialog.dismiss()
                    }
                    else -> {}
                }
            }
        }
    }
}