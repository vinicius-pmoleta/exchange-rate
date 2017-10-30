package com.exchangerate.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ForActivity

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ForApplication
