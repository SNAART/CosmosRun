package com.example.cosmosrun.de_injection


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.cosmosrun.database.RunningDatabase
import com.example.cosmosrun.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.cosmosrun.other.Constants.KEY_NAME
import com.example.cosmosrun.other.Constants.KEY_WEIGHT
import com.example.cosmosrun.other.Constants.RUNNING_DATABASE_NAME
import com.example.cosmosrun.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton //scope, get the same instance for all class
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) = sharedPreferences.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) = sharedPreferences.getString(KEY_WEIGHT, "72")

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences)
            = sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}
