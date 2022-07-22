package com.runway.routes.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings


fun openAppSystemSettings(context: Context) {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:" + context.packageName)
        addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_NO_HISTORY
                    or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        )
    }
    context.startActivity(intent)
}