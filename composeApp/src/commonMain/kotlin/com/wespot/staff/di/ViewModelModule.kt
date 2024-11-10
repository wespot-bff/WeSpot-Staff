package com.wespot.staff.di

import com.wespot.staff.vote.question.QuestionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModelOf(::QuestionViewModel)
}
