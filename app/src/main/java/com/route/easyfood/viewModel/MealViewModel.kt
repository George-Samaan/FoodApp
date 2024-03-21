package com.route.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.route.easyfood.pojo.MealList
import com.route.easyfood.pojo.Meals
import com.route.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {

    private var mealDetailLiveData = MutableLiveData<Meals>()

    fun getMealDetail(id: String) {
        RetrofitInstance.api.getMealDetails(id)
            .enqueue(object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response != null) {
                        mealDetailLiveData.value = response.body()?.meals?.get(0)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("MealActivity -> ", t.message.toString())
                }


            })
    }

    fun observeMealDetailLiveData(): LiveData<Meals> {
        return mealDetailLiveData
    }
}