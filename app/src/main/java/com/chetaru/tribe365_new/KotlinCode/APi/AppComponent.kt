package com.chetaru.tribe365_new.KotlinCode.APi

import com.google.android.datatransport.runtime.dagger.Component

@Component( modules = [ContextModule::class,RetrofitModule::class])
interface AppComponent {
}