package com.funrisestudio.buzzmessenger.core.di

import com.funrisestudio.buzzmessenger.Notifier
import com.funrisestudio.buzzmessenger.NotifierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface HiltModule {

    @Binds
    @Singleton
    fun notifier(notifierImpl: NotifierImpl): Notifier

}