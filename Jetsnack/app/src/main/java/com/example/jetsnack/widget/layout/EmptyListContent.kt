package com.example.platform.ui.appwidgets.glance.layout.collections.layout

import androidx.compose.runtime.Composable
import androidx.glance.LocalContext
import com.example.jetsnack.R
import com.example.jetsnack.widget.layout.NoDataContent
import com.example.jetsnack.widget.utils.ActionUtils

/**
 * Content to be displayed when there are no items in the list. To be displayed below the
 * app-specific title bar in the [androidx.glance.appwidget.components.Scaffold] .
 */
@Composable
internal fun EmptyListContent() {
  val context = LocalContext.current

  NoDataContent(
    noDataText = context.getString(R.string.app_name),
    noDataIconRes = R.drawable.empty_state_search,
    actionButtonText = context.getString(R.string.add_to_cart),
    actionButtonIcon = R.drawable.add_to_cart,
    actionButtonOnClick = ActionUtils.actionStartDemoActivity("on-click of add item button")
  )
}