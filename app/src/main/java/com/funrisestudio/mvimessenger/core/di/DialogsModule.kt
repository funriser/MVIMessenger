package com.funrisestudio.mvimessenger.core.di

import com.funrisestudio.mvimessenger.core.mvi.*
import com.funrisestudio.mvimessenger.core.navigation.NavAction
import com.funrisestudio.mvimessenger.core.navigation.Navigator
import com.funrisestudio.mvimessenger.domain.dialogs.DialogsRepository
import com.funrisestudio.mvimessenger.data.dialogs.DialogsRepositoryImpl
import com.funrisestudio.mvimessenger.domain.dialogs.DialogsMiddleware
import com.funrisestudio.mvimessenger.domain.dialogs.GetDialogsInteractor
import com.funrisestudio.mvimessenger.domain.dialogs.GetDialogsUseCase
import com.funrisestudio.mvimessenger.ui.dialogs.*
import com.funrisestudio.mvimessenger.ui.utils.ErrorHandler
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
interface DialogsModule {

    @Binds
    fun store(
        simpleStore: SimpleStore<DialogsAction, DialogsViewState>
    ): Store<DialogsAction, DialogsViewState>

    @Binds
    fun getDialogsUseCase(getDialogsInteractor: GetDialogsInteractor): GetDialogsUseCase

    @Binds
    fun repository(dialogsRepositoryImpl: DialogsRepositoryImpl): DialogsRepository

    @Binds
    fun middleware(dialogsMiddleware: DialogsMiddleware): MiddleWare<DialogsAction, DialogsViewState>

    @Binds
    fun reducer(dialogsReducer: DialogsReducer): Reducer<DialogsAction, DialogsViewState>

    @Binds
    fun errHandler(dialogsErrorHandler: DialogsErrorHandler): ErrorHandler

    @Binds
    fun navigator(dialogsNavigator: DialogsNavigator): Navigator<NavAction.DialogNavAction>

}