package com.funrisestudio.mvimessenger.core.di

import com.funrisestudio.mvimessenger.data.utils.CurrentDateProvider
import com.funrisestudio.mvimessenger.data.utils.DateProvider
import com.funrisestudio.mvimessenger.utils.Notifier
import com.funrisestudio.mvimessenger.utils.NotifierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface UtilsModule {

    @Binds
    @Singleton
    fun notifier(notifierImpl: NotifierImpl): Notifier

    @Binds
    fun dateProvider(currentDateProvider: CurrentDateProvider): DateProvider

}