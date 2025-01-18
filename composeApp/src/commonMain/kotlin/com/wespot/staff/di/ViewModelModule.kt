package com.wespot.staff.di

import com.wespot.staff.vote.question.QuestionViewModel
import com.wespot.staff.vote.write.add.QuestionAddViewModel
import com.wespot.staff.state.RootViewModel
import com.wespot.staff.entire.configuration.ConfigurationViewModel
import com.wespot.staff.entire.configuration.add.ConfigurationAddViewModel
import com.wespot.staff.entire.notification.NotificationViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModelOf(::QuestionViewModel)
    viewModelOf(::QuestionAddViewModel)
    viewModelOf(::ConfigurationViewModel)
    viewModelOf(::ConfigurationAddViewModel)
    viewModelOf(::NotificationViewModel)
    viewModelOf(::RootViewModel)
}
