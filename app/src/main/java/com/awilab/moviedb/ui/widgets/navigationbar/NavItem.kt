package com.awilab.moviedb.ui.widgets.navigationbar

import androidx.annotation.StringRes
import com.awilab.moviedb.R

sealed class NavItem(
    @StringRes val title: Int
) {
    data object Home : NavItem(
        title = R.string.lab_home
    )

    data object Search : NavItem(
        title = R.string.lab_search
    )
}