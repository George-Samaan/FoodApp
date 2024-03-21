package com.route.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.route.easyfood.pojo.CategoriesItem
import com.route.easyfood.pojo.CategoriesList
import com.route.easyfood.pojo.MealList
import com.route.easyfood.pojo.Meals
import com.route.easyfood.pojo.MealsByCategory
import com.route.easyfood.pojo.MealsByCategoryList
import com.route.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meals>()
    private var populatItemsLiveData = MutableLiveData<List<MealsByCategory?>?>()
    private var categoriesLiveData = MutableLiveData<List<CategoriesItem?>?>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandoMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meals? = response.body()!!.meals!![0]
                    randomMealLiveData.value = randomMeal!!
                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoriesList> {
            override fun onResponse(
                call: Call<CategoriesList>,
                response: Response<CategoriesList>
            ) {
                response.body()?.let { categoriesList ->
                    categoriesLiveData.postValue(categoriesList.categories)
                }

            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Log.d("HomeFragment - CategorySection ", t.message.toString())
            }

        })
    }

    fun getPopularitems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        populatItemsLiveData.value = response.body()?.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment (Popular Item)", t.message.toString())
                }

            })
    }

    fun observeRandomMealLiveDate(): LiveData<Meals> {
        return randomMealLiveData
    }

    fun observepopularItemsLiveData(): LiveData<List<MealsByCategory?>?> {
        return populatItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<CategoriesItem?>?> {
        return categoriesLiveData
    }
}