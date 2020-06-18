package com.funrisestudio.buzzmessenger.core.di

import com.funrisestudio.buzzmessenger.data.dialogs.DialogsRepository
import com.funrisestudio.buzzmessenger.data.dialogs.DialogsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DialogsModule {

    @Binds
    fun repository(dialogsRepositoryImpl: DialogsRepositoryImpl): DialogsRepository

}