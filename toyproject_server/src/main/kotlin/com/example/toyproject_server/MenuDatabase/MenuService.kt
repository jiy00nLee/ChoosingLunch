package com.example.toyproject_server.MenuDatabase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class MenuService {

    private val menuRepository : MenuRepository

    @Autowired
    constructor(menuRepository: MenuRepository){
        this.menuRepository = menuRepository
    }

    fun makeMenus(storelist_ids : MutableList<String>){
        val madeMenus = mapOf<String, Int>("사과" to 2000, "치킨" to 18000, "쉑쉑버거" to 5800,
        "로제파스타" to 8000, "밀크쉐이크" to 4300, "돼지국밥" to 5800)
        storelist_ids.forEach { storeid ->
            madeMenus.forEach { menuname, menuprice ->
                val menu : Menu = Menu()
                menu.storeid = storeid
                menu.name = menuname
                menu.price =menuprice
                menuRepository.save(menu)
            }
        }
    }

    fun findStoreID(storeID : String) : List<StoreMenuItem>? {
        val foundmenulist : List<Menu>? = menuRepository.findByStoreid(storeID)
        return foundmenulist?.map { it -> mappingMenutoStoreMenuItem(it) }
    }


    private fun mappingMenutoStoreMenuItem(menu: Menu) : StoreMenuItem {
        val storeMenuItem : StoreMenuItem = StoreMenuItem()
        storeMenuItem.storeid = menu.storeid
        storeMenuItem.menuname = menu.name
        storeMenuItem.menuprice = menu.price
        return(storeMenuItem)
    }
/*
    //여기서 함수이름은 노상관 인듯? (ex. join)
    fun saveAll(query: String?, userLng : Double, userLat: Double)  {
        var page_num : Int = 1
        val kakaoapi = KaKaoAPI.create()
        var resultSearchedData : ResultSearchKeyword?
        var meta : PlaceMeta?
        var places : List<PlaceDocument>?
        var transed_places : List<Place>?

        while (true){

            resultSearchedData = kakaoapi.getSearchLocationFromKakaoServer(query, userLat, userLng, page_num).execute().body()
            meta = resultSearchedData?.meta
            places = resultSearchedData?.documents
            transed_places = places?.map {it -> mappingPlaceDocumenttoPlace(it)}

            transed_places?.forEach { it -> placeRepository.save(it) } //placeRepository.saveAll(transed_places5) //이거 왜 안되는지 모르겠다.
            page_num = page_num + 1
            if (meta?.is_end == true) break
        }

    }
*/



}