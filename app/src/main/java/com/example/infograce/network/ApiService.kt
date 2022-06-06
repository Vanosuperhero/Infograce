package com.example.infograce.network

import com.example.infograce.network.models.TilesetsItemDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //НЕ НАЧИНАТЬ С СИМВОЛА "/"

    @GET("tilesets/v1/{username}")
    suspend fun getListOfTilesets(
        @Path(QUERY_PATH_USERNAME) username: String = QUERY_DEFAULT_USERNAME,
        @Query(QUERY_ACCESS_TOKEN) access_token: String = QUERY_DEFAULT_ACCESS_TOKEN,
    ): List<TilesetsItemDTO>

    companion object {
        private const val QUERY_ACCESS_TOKEN = "access_token"
        private const val QUERY_DEFAULT_ACCESS_TOKEN = "sk.eyJ1IjoiYXptZXRvdiIsImEiOiJjbDNxMGowYmMxMGNtM2tsbm12c2Q4aHllIn0.IvH_qCin0TJZIV_mf_KB4A"
        private const val QUERY_PATH_USERNAME = "username"
        private const val QUERY_DEFAULT_USERNAME = "azmetov"
    }
}