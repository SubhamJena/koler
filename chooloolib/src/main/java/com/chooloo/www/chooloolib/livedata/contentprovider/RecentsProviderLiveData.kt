package com.chooloo.www.chooloolib.livedata.contentprovider

import android.content.Context
import com.chooloo.www.chooloolib.contentresolver.RecentsContentResolver
import com.chooloo.www.chooloolib.di.factory.contentresolver.ContentResolverFactory
import com.chooloo.www.chooloolib.model.RecentAccount

class RecentsProviderLiveData(
    context: Context,
    private val recentId: Long? = null,
    private val contentResolverFactory: ContentResolverFactory
) : ContentProviderLiveData<RecentsContentResolver, RecentAccount>(context) {
    override val contentResolver by lazy { contentResolverFactory.getRecentsContentResolver(recentId) }
}