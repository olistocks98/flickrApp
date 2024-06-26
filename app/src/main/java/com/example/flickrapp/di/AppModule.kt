package com.example.flickrapp.di

import com.example.flickrapp.constants.FLICKR_BASE_URL
import com.example.flickrapp.data.FlickrApi
import com.example.flickrapp.data.repository.PhotoRepositoryImpl
import com.example.flickrapp.domain.repository.PhotoRepository
import com.example.flickrapp.domain.usecase.SearchPhotosUseCase
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePhotoApi(): FlickrApi =
        Retrofit
            .Builder()
            .baseUrl(FLICKR_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create(),
                ),
            ).client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }).build(),
            ).build()
            .create(FlickrApi::class.java)

    @Provides
    @Singleton
    fun providePhotoRepository(photoApi: FlickrApi): PhotoRepository = PhotoRepositoryImpl(photoApi)

    @Provides
    @Singleton
    fun provideSearchPhotoUseCase(searchPhotosRepository : PhotoRepository) : SearchPhotosUseCase =  SearchPhotosUseCase(searchPhotosRepository)
}
