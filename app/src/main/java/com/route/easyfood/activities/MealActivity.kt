package com.route.easyfood.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.route.easyfood.R
import com.route.easyfood.databinding.ActivityMealBinding
import com.route.easyfood.fragments.HomeFragment.Companion.MEAL_ID
import com.route.easyfood.fragments.HomeFragment.Companion.MEAL_NAME
import com.route.easyfood.fragments.HomeFragment.Companion.MEAL_THUMB
import com.route.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealName: String
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(
            this
        )

        { meal ->
            onResponseCase()
            binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
            binding.tvAreaInfo.text = "Area : ${meal.strArea}"
            binding.tvInstructionSteps.text = meal.strInstructions
            youtubeLink = meal.strYoutube.toString()
        }
    }

    private fun setInformationInViews() {
        Glide.with(this)
            .load(mealThumb)
            .into(binding.imvMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MEAL_ID)!!
        Log.e("CHECKING", "$mealId")
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.linearProgressBar.visibility = View.VISIBLE
        binding.fabFavourite.visibility = View.INVISIBLE
        binding.tvInstructionSteps.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.linearProgressBar.visibility = View.INVISIBLE
        binding.fabFavourite.visibility = View.VISIBLE
        binding.tvInstructionSteps.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}