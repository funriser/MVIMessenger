package com.funrisestudio.mvimessenger.core.di

import android.content.Context
import com.funrisestudio.mvimessenger.domain.messages.MessengerRepository
import com.funrisestudio.mvimessenger.data.messages.MessengerRepositoryImpl
import com.funrisestudio.mvimessenger.data.messages.MessengerServiceController
import com.funrisestudio.mvimessenger.data.messages.MessengerServiceControllerImpl
import com.funrisestudio.mvimessenger.data.room.AppDatabase
import com.funrisestudio.mvimessenger.data.room.MessagesDao
import com.funrisestudio.mvimessenger.domain.messages.ReceiveMessageInteractor
import com.funrisestudio.mvimessenger.domain.messages.ReceiveMessageUseCase
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

    @Binds
    fun receiveMessagesUseCase(
        receiveMessageInteractor: ReceiveMessageInteractor
    ): ReceiveMessageUseCase

}