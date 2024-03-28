package org.yellowhatpro.thetaskapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.yellowhatpro.thetaskapp.data.TaskDatabase
import org.yellowhatpro.thetaskapp.data.api.ApiService
import org.yellowhatpro.thetaskapp.data.dao.TaskDao
import org.yellowhatpro.thetaskapp.data.repo.TaskRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "task_database"
    ).build()

    @Provides
    @Singleton
    fun provideTaskDao(
        database: TaskDatabase
    ): TaskDao = database.taskDao()

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit() : ApiService = Retrofit.Builder()
        .baseUrl("https://660be9f93a0766e85dbce1d6.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}