package com.example.toyproject_server.RestAPI


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface KaKaoAPI {

    companion object {
        private const val BASE_URL = "https://dapi.kakao.com/"

        fun create() : KaKaoAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor() //객체 생성

            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY //(위) 객체의 모드 설정(헤더+바디를 가져오는 모드이다.)

            val headerInterceptor = Interceptor {
                val request = it.request().newBuilder().addHeader("Authorization","KakaoAK c8781401349efdc68e8a2a85ed323876").build()
                return@Interceptor it.proceed(request)
            }

            //위의 두 intercepter를 합쳐서 'Client' 생성해줌.(헤더= 개인키, 바디 = 원하는 뽑아낸 데이터)
            val client = OkHttpClient.Builder().addInterceptor(headerInterceptor).addInterceptor(httpLoggingInterceptor).build()
            val retrofit : Retrofit =  Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
                GsonConverterFactory.create()).build()    //Retrofit 구성.

            return retrofit.create(KaKaoAPI::class.java)   //통신 인터페이스를 객체로 생성. (Service)
        }
    }



    @GET("v2/local/search/keyword.json") //keyword.json의 정보를 받아옴.
    fun getSearchLocationFromKakaoServer(  //가져올때 덧붙일 조건!(중요)
        @Query("query") keyword: String?, //[필수 구현] 검색을 원하는  (필수 != null, 입력은 받아야 하지만 입력값이 null이어도 된다 이말.)
        @Query("x") longitude: Double,  //[필수 구현] 입력받음(정보 o)
        @Query("y") latitude: Double,   //[필수 구현] 입력받음(정보 o)
        @Query("page") page : Int?, // [선택] 입력받음.
        @Query("radius") radius: Int = 2000, //[필수 구현] 입력은 없지만 알아서 넣어줌(정보 o)
        @Query("sort") sortmode : String = "accuracy", // 정렬모드 : 기본 accuracy (or distance)
        @Query("size") size: Int = 15, // (최대) -한 문서당.
        @Query("category_group_code") category_group_code: String = "FD6", //알아서 넣어줌.
    ): Call<ResultSearchKeyword> //Entity 연결이 필요하다.
    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김.

    //@DELETE -> 일단 이거 접어두고..하기..ㅠㅠ


}
