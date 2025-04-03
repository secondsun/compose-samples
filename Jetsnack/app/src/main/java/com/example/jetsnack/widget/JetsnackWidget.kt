package com.example.jetsnack.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import com.example.jetsnack.R
import com.example.jetsnack.model.SnackRepo
import com.example.jetsnack.widget.layout.ImageTextListItemData
import com.example.jetsnack.widget.layout.ImageTextListLayout
import com.example.jetsnack.widget.utils.AspectRatio
import com.example.jetsnack.widget.utils.AspectRatio.Companion.asDouble
import com.example.jetsnack.widget.utils.ImageUtils
import com.example.jetsnack.widget.utils.ImageUtils.getMaxWidgetMemoryAllowedSizeInBytes

class JetsnackWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = JetsnackWidget()
}

class JetsnackWidget: GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            val context = LocalContext.current

            val maxAllowedBytes = context.getMaxWidgetMemoryAllowedSizeInBytes()
            val maxAllowedBytesPerImage = maxAllowedBytes / 36

            val imageSize = ImageUtils.getMaxPossibleImageSize(
                aspectRatio = AspectRatio.Ratio1x1.asDouble(),
                memoryLimitBytes = maxAllowedBytesPerImage,
                maxImages = 1)

            Log.d("Widget", "Image size: $imageSize")

            val items = SnackRepo.getSnacks().flatMap { it.snacks }
                .subList(0,5)
                .map { ImageTextListItemData(
                    key = it.name,
                    title = it.name,
                    supportingText = it.tagline,
                    supportingImageBitmap = BitmapFactory.decodeResource(context.resources, it.imageRes,
                        BitmapFactory.Options().apply {
                            this.outWidth = 101
                            this.outHeight = 101
                        }),
                    trailingIconButton = R.drawable.add_to_cart,
                    trailingIconButtonContentDescription = "Add to cart"
                ) }
            GlanceTheme {
                ImageTextListLayout(
                    items = items,
                    title = context.getString(
                        R.string.app_name
                    ),
                    titleIconRes = R.drawable.ic_launcher_foreground,
                    titleBarActionIconRes = R.drawable.search,
                    titleBarActionIconContentDescription = context.getString(
                        R.string.search_jetsnack
                    ),
                    titleBarAction = {},
                )
            }
        }
    }
}