package ddwu.com.mobile.foodexam

import android.app.ComponentCaller
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.foodexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivityTAG"
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: FoodAdapter
    val FOOD_ADD_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foods = FoodDao.foods

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = FoodAdapter(foods)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        // btnAdd를 클릭하면 AddFoodActivity 실행
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivityForResult(intent, FOOD_ADD_REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == FOOD_ADD_REQUEST_CODE){
            when(resultCode){
                RESULT_OK -> {
                    val food = data?.getStringExtra("food")
                    val country = data?.getStringExtra("country")
                    if(!food.isNullOrEmpty() && !country.isNullOrEmpty()){
                        FoodDao.foods.add(FoodDto(food, country))
                        adapter.notifyDataSetChanged()
                    }
                }
                RESULT_CANCELED -> {
                    Log.i(TAG, "Result canceled")
                }
            }
        }
    }
}