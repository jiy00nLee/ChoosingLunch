package com.example.toyproject_client.myserver

import android.view.MenuItem
import com.example.toyproject_client.data.StoreMenuItem
import com.example.toyproject_client.data.UserData.UserDataEntity
import com.example.toyproject_client.data.UserData.UserLocationItemData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Myserver {

    companion object {
        private const val BASE_URL = "http:/192.168.110.79:8080"   //내 서버 링크와 연결.

        fun create(): Myserver {

            //네트워크와 OkHttp 코어 사이에서 요청/응답
            val httpLoggingInterceptor = HttpLoggingInterceptor() //객체 생성
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY //(위) 로깅 모드 설정(None,Basic,Header, 'Body')

            //어플리케이션 내에서 OkHttp 코어사이에서 요청/응답을 가로채는 역할을 함.
            val headerInterceptor = Interceptor {
                val request = it.request().newBuilder().build() //토큰 없?
                return@Interceptor it.proceed(request)
            }
            //위의 두 intercepter를 합쳐서 'Client' 생성해줌.(헤더= 개인키, 바디 = 원하는 뽑아낸 데이터)
            val client = OkHttpClient.Builder().addInterceptor(headerInterceptor).addInterceptor(httpLoggingInterceptor).build()


            val retrofit : Retrofit =  Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()    //Retrofit 구성.
            val server : Myserver = retrofit.create(Myserver::class.java)
            return server   //통신 인터페이스를 객체로 생성. (Service)
        }
    }

    @GET("/myServerPlaceDatabase") // 내서버의 특정링크로부터 가져올 것.
    fun getSearchLocationFromMyserverDatabase(
        @Query("query") keyword: String?, //[필수 구현] 검색을 원하는  (필수 != null, 입력은 받아야 하지만 입력값이 null이어도 된다 이말.)
        @Query("x") longitude: Double,  //[필수 구현] 입력받음(정보 o)
        @Query("y") latitude: Double,
        @Query("userAddress")  useraddress: String
    ): Call<List<PlaceDocument>>

    @GET("/myServerMenuDatabase")
    fun getSearchStoreMenuFromMyserverDatabase(
        @Query("storeID") storeid : String
    ) : Call<List<StoreMenuItem>>

    @POST("/insertmyServerpaymentDatabase")
    fun InsertbuyStoreMenuInfoToMyserverDatabase( @Body body: PayInfoItem) : Call<PayInfoItem>



    /*
    @POST("/myServerDatabase")  //이거 체킹이 필요하다.
    fun SendSearchLocationToMyserver (
        @Query("query") keyword: String?,
        @Query("X") longtitude : Double,
        @Query("Y") latitude: Double                                    //Post하는 방법 찾아봐야할듯.
    ): Call<List<PlaceDocument>> //Response로 바꿀 생각도 해보기 (수정!)
*/
    //*[Retrofit] Call과 Response의 차이 -> Call : Response + 비동기 처리 / Response : 동기처리 (따라서 추가적으로 코루틴이나 rxjava가 필요하다.)
}