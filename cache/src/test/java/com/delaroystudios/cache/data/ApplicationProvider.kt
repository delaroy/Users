package com.delaroystudios.cache.data

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

object ApplicationProvider {
    /**
     * Returns the application [Context] for the application under test.
     *
     * @see {@link Context.getApplicationContext
     */
    fun <T : Context?> getApplicationContext(): T {
        return InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as T
    }
}