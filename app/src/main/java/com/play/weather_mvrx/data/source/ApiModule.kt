package com.play.weather_mvrx.data.source

import com.google.gson.GsonBuilder
import com.play.weather_mvrx.data.repository.WeatherRepositoryImp
import com.play.weather_mvrx.domain.WeatherRepository
import com.play.weather_mvrx.utils.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideWeatherRepository( apiService: ApiService): WeatherRepository {
//        return WeatherRepositoryImp(apiService)
//    }

}