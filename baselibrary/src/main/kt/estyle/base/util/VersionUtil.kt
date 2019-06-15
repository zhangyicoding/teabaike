package estyle.base.util

import android.content.Context

object VersionUtil {

    fun needUpdate(context: Context, newVersionCode: Long) =
        newVersionCode > getCurrentVersionCode(context)

    private fun getCurrentVersionCode(context: Context) =
        context.packageManager
            .getPackageInfo(context.packageName, 0)
            .versionCode

    fun getCurrentVersionName(context: Context) =
        context.packageManager
            .getPackageInfo(context.packageName, 0)
            .versionName
}