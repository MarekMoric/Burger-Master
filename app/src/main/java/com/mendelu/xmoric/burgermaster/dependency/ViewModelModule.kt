package com.mendelu.xmoric.burgermaster.dependency

import androidx.lifecycle.SavedStateHandle
import com.mendelu.xmoric.burgermaster.ui.screens.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // for screens
    //todo dont forget get
    viewModel { ListScreenViewModel(get()) }
    viewModel { CreationScreenViewModel(get()) }
    viewModel { DetailScreenViewModel(get()) }
    viewModel { ProfileScreenViewModel() }
    viewModel { NutritionScreenViewModel(get()) }
    viewModel { MapScreenViewModel(get()) }

    // For the saved state handle
    fun provideSavedStateHandle(): SavedStateHandle{
        return SavedStateHandle()
    }

    factory { provideSavedStateHandle() }

}
