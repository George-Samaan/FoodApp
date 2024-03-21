package com.route.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.route.easyfood.activities.MealActivity
import com.route.easyfood.adapters.CategoriesAdapter
import com.route.easyfood.adapters.MostPopularAdapter
import com.route.easyfood.databinding.FragmentHomeBinding
import com.route.easyfood.pojo.CategoriesItem
import com.route.easyfood.pojo.Meals
import com.route.easyfood.pojo.MealsByCategory
import com.route.easyfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeals: Meals
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesItemAdapter: CategoriesAdapter


    companion object {
        const val MEAL_ID = "MEAL_ID"
        const val MEAL_NAME = "MEAL_NAME"
        const val MEAL_THUMB = "MEAL_THUMB"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
        categoriesItemAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularitems()
        observerpopularItems()
        onPopulatItemClick()


        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesLiveData()


    }

    private fun prepareCategoriesRecyclerView() {
        binding.rvViewsCategory.apply {
            layoutManager = GridLayoutManager(
                activity, 3,
                GridLayoutManager.VERTICAL, false
            )
            adapter = categoriesItemAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesItemAdapter.setCategoryList(
                categories as ArrayList<CategoriesItem>
            )
        })
    }

    private fun onPopulatItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.rvViewMeals.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }

    }

    private fun observerpopularItems() {
        homeMvvm.observepopularItemsLiveData().observe(
            viewLifecycleOwner
        ) { meals ->
//            meals?.forEach { mealsByCategory ->
//                Log.d("X", meals.size.toString())
//
//            }
            popularItemsAdapter.setMeals(
                mealsList = meals as ArrayList<MealsByCategory>
            )
        }

    }


    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeals.idMeal)
            intent.putExtra(MEAL_NAME, randomMeals.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeals.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveDate().observe(
            viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imvRandomMeal)

            this.randomMeals = value
        }
    }


}