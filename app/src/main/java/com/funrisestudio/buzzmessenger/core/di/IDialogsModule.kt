package com.funrisestudio.buzzmessenger.core.di

import com.funrisestudio.buzzmessenger.core.mvi.DefaultStore
import com.funrisestudio.buzzmessenger.core.mvi.MiddleWare
import com.funrisestudio.buzzmessenger.core.mvi.Reducer
import com.funrisestudio.buzzmessenger.core.mvi.Store
import com.funrisestudio.buzzmessenger.data.dialogs.DialogsRepository
import com.funrisestudio.buzzmessenger.data.dialogs.DialogsRepositoryImpl
import com.funrisestudio.buzzmessenger.domain.dialogs.DialogsMiddleware
import com.funrisestudio.buzzmessenger.ui.main.DialogsAction
import com.funrisestudio.buzzmessenger.ui.main.DialogsErrorHandler
import com.funrisestudio.buzzmessenger.ui.main.DialogsReducer
import com.funrisestudio.buzzmessenger.ui.main.DialogsViewState
import com.funrisestudio.buzzmessenger.ui.utils.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface IDialogsModule {

    @Binds
    fun store(
        defaultStore: DefaultStore<DialogsAction, DialogsViewState>
    ): Store<DialogsAction, DialogsViewState>

    @Binds
    fun repository(dialogsRepositoryImpl: DialogsRepositoryImpl): DialogsRepository

    @Binds
    fun middleware(dialogsMiddleware: DialogsMiddleware): MiddleWare<DialogsAction, DialogsViewState>

    @Binds
    fun reducer(dialogsReducer: DialogsReducer): Reducer<DialogsAction, DialogsViewState>

    @Binds
    fun errHandler(dialogsErrorHandler: DialogsErrorHandler): ErrorHandler

}

@Module
@InstallIn(ActivityRetainedComponent::class)
object DialogsModule {

    @Provides
    fun initialState(): DialogsViewState {
        return DialogsViewState.createEmpty()
    }

}