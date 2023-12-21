package com.pokemon.ui.view.splash

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import com.kimiafarma.marvee.feature_onboarding.ui.fragments.splash.SplashViewModel
import com.pokemon.shared.base.BaseFragment
import com.pokemon.shared.extension.launchWhenResumed
import com.pokemon.shared.extension.navigate
import com.pokemon.ui.R
import com.pokemon.ui.databinding.FragmentSplashScreenBinding
import com.pokemon.ui.enums.RouteEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animatedSplashScreen()
        observeDelayState()
        viewModel.setDelayScreen(2)
    }

    private fun animatedSplashScreen() {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.blink)
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.duration = 700
        binding.ivPokeball.startAnimation(animation)
    }

    private fun navigateToListPokemon() {
        val navOption = NavOptions.Builder().setPopUpTo(R.id.splash_fragment, true).build()
        navigate(route = RouteEnum.LIST_POKEMON.getRouteName(this), navOptions = navOption)
    }

    private fun observeDelayState() {
        launchWhenResumed {
            viewModel.delayState.collectLatest {
                if (!it) {
                    navigateToListPokemon()
                }
            }
        }
    }

}