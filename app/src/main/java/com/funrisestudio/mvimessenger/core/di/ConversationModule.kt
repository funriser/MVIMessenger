package com.funrisestudio.mvimessenger.core.di

import com.funrisestudio.mvimessenger.core.mvi.*
import com.funrisestudio.mvimessenger.data.conversation.ConversationRepositoryImpl
import com.funrisestudio.mvimessenger.domain.conversation.*
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.funrisestudio.mvimessenger.ui.conversation.ConversationReducer
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewState
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Module
@InstallIn(ActivityRetainedComponent::class)
interface IConversationModule {

    @Binds
    fun store(
        store: SimpleStore<ConversationAction, ConversationViewState>
    ): Store<ConversationAction, ConversationViewState>

    @Binds
    fun getConversationUseCase(
        getConversationInteractor: GetConversationInteractor
    ): GetConversationUseCase

    @Binds
    fun sendMessageUseCase(
        sendMessageInteractor: SendMessageInteractor
    ): SendMessageUseCase

    @Binds
    fun markAsReadUseCase(
        markAsReadInteractor: MarkAsReadInteractor
    ): MarkAsReadUseCase

    @Binds
    fun repository(
        conversationRepositoryImpl: ConversationRepositoryImpl
    ): ConversationRepository

    @Binds
    fun middleware(
        conversationMiddleware: ConversationMiddleware
    ): MiddleWare<ConversationAction, ConversationViewState>

    @Binds
    fun reducer(
        conversationReducer: ConversationReducer
    ): Reducer<ConversationAction, ConversationViewState>

}

@Module
@InstallIn(ActivityRetainedComponent::class)
object ConversationModule {

    @Provides
    fun initialState(): ConversationViewState {
        return ConversationViewState.createEmpty()
    }

}