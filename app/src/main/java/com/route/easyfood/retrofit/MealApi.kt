package com.route.easyfood.retrofit

import com.route.easyfood.pojo.CategoriesList
import com.route.easyfood.pojo.MealList
import com.route.easyfood.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandoMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(
        @Query("i") id: String
    ): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(
        @Query("c") categoryName: String
    ): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoriesList>
}