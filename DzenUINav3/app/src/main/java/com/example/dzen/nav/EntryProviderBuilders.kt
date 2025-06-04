package com.example.dzen.nav

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import com.example.dzen.ui.theme.screen.onboarding_screen.OnboardScreen
import com.example.dzen.ui.theme.screen.user_screen.component.ChangePasswordScreen
import com.example.dzen.ui.theme.screen.user_screen.component.LanguageScreen
import com.example.dzen.ui.theme.screen.user_screen.component.TermsConditionsScreen

fun <T : Any> EntryProviderBuilder<T>.addUserNavEntries(navBackStack: NavBackStack) {
    entry<UserNav.LanguageScreen> {
        LanguageScreen(onBackClick = { navBackStack.removeLastOrNull() })
    }
    entry<UserNav.ChangePasswordScreen> {
        ChangePasswordScreen(onBackClick = { navBackStack.removeLastOrNull() })
    }
    entry<UserNav.TermsConditionsScreen> {
        TermsConditionsScreen(onBackClick = { navBackStack.removeLastOrNull() })
    }
}

fun <T : Any> EntryProviderBuilder<T>.onBoardNavEntries(navBackStack: NavBackStack) {
    entry<OnBoardingScreen> {
        OnboardScreen(navBackStack = navBackStack)
    }
}