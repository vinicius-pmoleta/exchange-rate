package com.exchangerate.core.structure.middleware

import com.crashlytics.android.Crashlytics
import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviMiddleware
import com.exchangerate.core.structure.MviState

class CrashMiddleware : MviMiddleware {

    override fun intercept(oldState: MviState, action: MviAction, newState: MviState?) {
        Crashlytics.log("$oldState + $action = $newState")
        Crashlytics.setString("old_state", "$oldState")
        Crashlytics.setString("action", "$action")
        Crashlytics.setString("new_state", "$newState")
    }
}