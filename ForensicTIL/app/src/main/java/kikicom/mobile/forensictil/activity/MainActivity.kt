package kikicom.mobile.forensictil.activity

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DatePickerDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kikicom.mobile.forensictil.R
import kikicom.mobile.forensictil.adapter.RecordAdapter
import kikicom.mobile.forensictil.dao.RecordDao
import kikicom.mobile.forensictil.data.ForensicRecord
import kikicom.mobile.forensictil.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recordDao: RecordDao
    private lateinit var adapter: RecordAdapter
    private var recordList: List<ForensicRecord> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recordDao = RecordDao(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshRecordList()
    }

    private fun refreshRecordList(){
        recordList = recordDao.getAllRecords()
        adapter = RecordAdapter(recordList,
            onItemClick = { selected ->
            val intent = Intent(this, UpdateRecordActivity::class.java)
            intent.putExtra("record", selected)
            startActivity(intent)
            },
            onItemLongClick = { selected ->
                AlertDialog.Builder(this)
                    .setTitle("기록 삭제")
                    .setMessage("이 기록을 삭제하시겠습니까?")
                    .setPositiveButton("삭제"){ _, _ ->
                        recordDao.deleteRecord(selected.id)
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        refreshRecordList()
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        )
        binding.rvRecords.adapter = adapter
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sort_recent -> {
                recordList = recordList.sortedByDescending { it.date }
                refreshListManually()
            }
            R.id.sort_old -> {
                recordList = recordList.sortedBy { it.date }
                refreshListManually()
            }
            R.id.filter_by_date -> {
                showDatePicker()
            }
            R.id.menu_calendar -> {
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshListManually(){
        adapter = RecordAdapter(
            recordList,
            onItemClick = { selected ->
                val intent = Intent(this, UpdateRecordActivity::class.java)
                intent.putExtra("record", selected)
                startActivity(intent)
            },
            onItemLongClick = { selected ->
                AlertDialog.Builder(this)
                    .setTitle("기록 삭제")
                    .setMessage("이 기록을 삭제하시겠습니까?")
                    .setPositiveButton("삭제"){ _, _ ->
                        recordDao.deleteRecord(selected.id)
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        refreshRecordList()
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        )
        binding.rvRecords.adapter = adapter
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView

        searchView?.queryHint = "주제, 도구, 내용 검색"

        searchView?.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
             query?.let {filterRecords(it)}
             return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {filterRecords(it)}
                return true
            }
        })
        return true
    }

    private fun filterRecords(query: String){
        val filteredList = recordList.filter {
            it.topic.contains(query, ignoreCase = true) ||
                    it.tool.contains(query, ignoreCase = true) ||
                    it.content.contains(query, ignoreCase = true)
        }
        adapter = RecordAdapter(filteredList,
            onItemClick = { selected ->
                val intent = Intent(this, UpdateRecordActivity::class.java)
                intent.putExtra("record", selected)
                startActivity(intent)
            },
            onItemLongClick = { selected ->
                AlertDialog.Builder(this)
                    .setTitle("기록 삭제")
                    .setMessage("이 기록을 삭제하시겠습니까?")
                    .setPositiveButton("삭제"){ _, _ ->
                        recordDao.deleteRecord(selected.id)
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        refreshRecordList()
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        )
        binding.rvRecords.adapter = adapter
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
    }
    private fun showDatePicker(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(this, { _, y, m, d ->
            val selectedDate = String.format("%04d-%02d-%02d", y, m+1, d)
            filterByDate(selectedDate)
        }, year, month, day)
        datePickerDialog.show()
    }
    private fun filterByDate(date: String){
        val filteredList = recordList.filter { it.date == date }
        adapter = RecordAdapter(filteredList,
            onItemClick = { selected ->
                val intent = Intent(this, UpdateRecordActivity::class.java)
                intent.putExtra("record", selected)
                startActivity(intent)
            },
            onItemLongClick = { selected ->
                AlertDialog.Builder(this)
                    .setTitle("기록 삭제")
                    .setMessage("이 기록을 삭제하시겠습니까?")
                    .setPositiveButton("삭제"){ _, _ ->
                        recordDao.deleteRecord(selected.id)
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        refreshRecordList()
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        )
        binding.rvRecords.adapter = adapter
        binding.rvRecords.layoutManager = LinearLayoutManager(this)
    }
}