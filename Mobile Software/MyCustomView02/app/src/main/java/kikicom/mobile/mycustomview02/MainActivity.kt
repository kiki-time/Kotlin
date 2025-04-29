package kikicom.mobile.mycustomview02

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kikicom.mobile.mycustomview02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // MyCustomView의 canvas를 longClick 했을 경우 AlertDialog를 띄우는 코드

        var selectedIndex = 0
        binding.myCustomView.setOnLongClickListener{
            val builder : AlertDialog.Builder = AlertDialog.Builder(this).apply{
                setTitle("색상 선택")
                setSingleChoiceItems(R.array.colors, selectedIndex) { _, index ->
                    selectedIndex = index
                }
                setPositiveButton("확인"){ _, _ ->
                    binding.myCustomView.setSelectColor(selectedIndex)
                    binding.myCustomView.invalidate()
                }
                setNegativeButton("취소", null)
            }
            builder.show()
            true
        }
    }
}