package kikicom.mobile.customviewtest

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kikicom.mobile.customviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding // activity_main.xml과 연결


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater) // binding을 실제 객체로 만들기
        setContentView(binding.root)


        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun onCreate(view: View){
        binding = ActivityMainBinding.inflate(layoutInflater) // binding을 실제 객체로 만들기
        setContentView(binding.root)

        binding.myCustomView.radius = 200.0f
        binding.myCustomView.invalidate() // 다시 그리는 함수, 화면을 무효화함(간접적으로 다시 시킴)
    }
}