package br.com.github.dimrsilva.giphy.infrastructure

import android.content.Context
import androidx.room.Room
import br.com.github.dimrsilva.giphy.infrastructure.database.GifDatabase
import br.com.github.dimrsilva.giphy.infrastructure.http.GiphyInterceptor
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

private val databaseModule = module {
    factory {
        Room.databaseBuilder(get(), GifDatabase::class.java, "gifs")
            .build()
    }
}

private val httpModule = module {
    factory {
        OkHttpClient.Builder()
            .addInterceptor(GiphyInterceptor())
            .build()
    }
    factory {
        Retrofit.Builder()
            .baseUrl("https://api.giphy.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }

    single<Cache> {
        val context = get<Context>()

        // Specify cache folder, my cache folder named media which is inside getCacheDir.
        val cacheFolder = File(context.filesDir, "gifs-cache")

        // Specify cache size and removing policies
        val cacheEvictor = LeastRecentlyUsedCacheEvictor(500 * 1024 * 1024)
        // My cache size will be 500MB and it will automatically remove least recently used files if the size is reached out.

        val databaseProvider = ExoDatabaseProvider(context)

        // Build cache
        SimpleCache(cacheFolder, cacheEvictor, databaseProvider)
    }

    factory<DataSource.Factory> {
        // Build data source factory with cache enabled, if data is available in cache it will return immediately,
        // otherwise it will open a new connection to get the data.
        CacheDataSource.Factory()
            .setCache(get())
            .setUpstreamDataSourceFactory(DefaultHttpDataSourceFactory())
    }

    factory<MediaSourceFactory> {
        ProgressiveMediaSource.Factory(get())
    }
}

val infrastructureModules = listOf(databaseModule, httpModule)