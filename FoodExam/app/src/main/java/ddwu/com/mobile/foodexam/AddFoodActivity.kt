package ddwu.com.mobile.foodexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.foodexam.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {
    val TAG = "AddFoodActivityTAG"
    lateinit var binding: ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // btnSave를 클릭하면 새로 생성한 FoodDto를 결과 intent에 저장 후 종료,
        // Food의 원본데이터 추가후 갱신
        binding.btnSave.setOnClickListener {
            val food = binding.etNewFood.text.toString()
            val country = binding.etCountry.text.toString()

            if (food.isNotEmpty() && country.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("food", food)
                intent.putExtra("country", country)
                setResult(RESULT_OK, intent)
            } else{
                setResult(RESULT_CANCELED)
            }

            finish()
        }

        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}