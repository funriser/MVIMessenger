package com.funrisestudio.buzzmessenger.core.di

import android.content.Context
import com.funrisestudio.buzzmessenger.data.messages.MessengerRepository
import com.funrisestudio.buzzmessenger.data.messages.MessengerRepositoryImpl
import com.funrisestudio.buzzmessenger.data.MessengerServiceController
import com.funrisestudio.buzzmessenger.data.MessengerServiceControllerImpl
import com.funrisestudio.buzzmessenger.data.room.AppDatabase
import com.funrisestudio.buzzmessenger.data.room.MessagesDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.build(context)
    }

    @Provides
    @Singleton
    fun providesMessagesDao(appDatabase: AppDatabase): MessagesDao {
        return appDatabase.messagesDao()
    }

}

@Module
@InstallIn(ApplicationComponent::class)
interface IDataModule {

    @Binds
    fun messengerRepository(messengerRepositoryImpl: MessengerRepositoryImpl): MessengerRepository

    @Binds
    fun messengerController(
        messengerServiceControllerImpl: MessengerServiceControllerImpl
    ): MessengerServiceController

}