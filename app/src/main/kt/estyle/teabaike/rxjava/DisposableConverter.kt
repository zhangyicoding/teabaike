package estyle.teabaike.rxjava

import androidx.lifecycle.LifecycleOwner

import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

object DisposableConverter {

    fun <T> dispose(owner: LifecycleOwner): AutoDisposeConverter<T> =
        AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner))
}
