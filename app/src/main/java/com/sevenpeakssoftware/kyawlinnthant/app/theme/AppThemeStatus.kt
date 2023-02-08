package com.sevenpeakssoftware.kyawlinnthant.app.theme

sealed interface AppThemeStatus {
    object DynamicDark : AppThemeStatus
    object DynamicLight : AppThemeStatus
    object Dark : AppThemeStatus
    object Light : AppThemeStatus
}