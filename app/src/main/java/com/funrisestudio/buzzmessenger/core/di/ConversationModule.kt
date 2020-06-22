package com.funrisestudio.buzzmessenger.core.di

import com.funrisestudio.buzzmessenger.core.mvi.PresentationStore
import com.funrisestudio.buzzmessenger.core.mvi.Reducer
import com.funrisestudio.buzzmessenger.core.mvi.Store
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
        store: PresentationStore<ConversationAction, ConversationViewState>
    ): Store<ConversationAction, ConversationViewState>

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