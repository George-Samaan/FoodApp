package com.route.easyfood.pojo

import com.google.gson.annotations.SerializedName

data class CategoriesList(

    @field:SerializedName("categories")
    val categories: List<CategoriesItem?>? = null
)