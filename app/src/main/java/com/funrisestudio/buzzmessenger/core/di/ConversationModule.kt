package com.funrisestudio.buzzmessenger.core.di

import com.funrisestudio.buzzmessenger.core.mvi.*
import com.funrisestudio.buzzmessenger.data.conversation.ConversationRepositoryImpl
import com.funrisestudio.buzzmessenger.domain.conversation.ConversationMiddleware
import com.funrisestudio.buzzmessenger.domain.conversation.ConversationRepository
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationAction
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationReducer
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationViewState
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
        store: DefaultStore<ConversationAction, ConversationViewState>
    ): Store<ConversationAction, ConversationViewState>

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