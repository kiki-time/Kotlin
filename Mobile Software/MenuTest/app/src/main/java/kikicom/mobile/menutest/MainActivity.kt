package kikicom.mobile.menutest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kikicom.mobile.menutest.databinding.ActivityMainBinding

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
        registerForContextMenu(binding.textView)
        registerForContextMenu(binding.myCustomView)
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_test, menu) // R.menu.menu_test로 생성
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item01 -> {
                Log.i(TAG, "Item01 항목 선택!")
                true // 마지막에 적혀있는 값이 리턴값
            }
            R.id.sub01 -> {
                Log.i(TAG, "하위메뉴 sub01 항목 선택!")
                true
            }
            R.id.sub02 -> {
                Log.i(TAG, "하위메뉴 sub02 항목 선택!")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean { // 실행이 되면서 이름이 NEW CHECK로 바뀐 것
        val checkItem02 :MenuItem? = menu?.findItem(R.id.check02) // findItem : id로 메뉴 아이템 항목을 찾아내는 것
        checkItem02?.title = "NEW CHECK" // title을 바꿔본 것
        return true
    }
    */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_foods, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.radio01 -> {
                Log.i(TAG, "돼지고기 김치찌개 짱")
                true
            }
            R.id.radio02 -> {
                Log.i(TAG, "순두부찌개에 무조건 계란 넣기")
                true
            }
            R.id.check01 -> {
                Log.i(TAG, "짜장면 보다 짬뽕이 맛있긴 함")
                true
            }
            R.id.check02 -> {
                Log.i(TAG, "파스타는 알리오올리오지..근데 매콤크림파스타도 맛있겠다")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when (v?.id){
            R.id.textView ->
                menuInflater.inflate(R.menu.menu_test, menu)
        }
    }
}
