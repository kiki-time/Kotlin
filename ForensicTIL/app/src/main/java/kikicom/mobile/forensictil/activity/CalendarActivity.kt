package kikicom.mobile.forensictil.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.MonthDayBinder
import kikicom.mobile.forensictil.R

import kikicom.mobile.forensictil.adapter.RecordAdapter
import kikicom.mobile.forensictil.dao.RecordDao
import kikicom.mobile.forensictil.data.ForensicRecord
import kikicom.mobile.forensictil.databinding.ActivityCalendarBinding
import java.time.DayOfWeek
import java.time.LocalDate

class CalendarActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var recordDao: RecordDao
    private lateinit var adapter: RecordAdapter
    private lateinit var allRecords: List<ForensicRecord>

    private var selectedDate: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "캘린더"

        binding.fabAddRecord.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }

        recordDao = RecordDao(this)
        allRecords = recordDao.getAllRecords()

        setupCalendar()
    }

    private fun setupCalendar(){
        val today = LocalDate.now()
        val startMonth = today.minusMonths(12).yearMonth
        val endMonth = today.plusMonths(12).yearMonth
        val firstDayOfWeek = DayOfWeek.SUNDAY

        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeek)

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()

                val hasRecord = allRecords.any { it.date == day.date.toString() }
                container.textView.setTextColor(
                    if (hasRecord) Color.BLUE else Color.DKGRAY
                )

                container.view.setOnClickListener {
                    showRecordsForDate(day.date)
                }
            }
        }
        binding.calendarView.monthScrollListener = { month ->
            val year = month.yearMonth.year
            val monthValue = month.yearMonth.monthValue
            binding.tvCurrentMonth.text = "${year}년 ${monthValue}월"
        }

        binding.calendarView.post {
            binding.calendarView.scrollToMonth(today.yearMonth)
            binding.tvCurrentMonth.text = "${today.year}년 ${today.monthValue}월"
        }
    }

    private fun showRecordsForDate(date: LocalDate) {
        // val records = allRecords.filter { it.date == date.toString() }
        selectedDate = date
        val records = allRecords.filter { it.date == date.toString() }
        adapter = RecordAdapter(records,
            onItemClick = { /* 이동 로직 */ },
            onItemLongClick = { /* 삭제 로직 */ }
        )
        binding.rvDailyRecords.adapter = adapter
        binding.rvDailyRecords.layoutManager = LinearLayoutManager(this)
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.dayText)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onResume() {
        super.onResume()
        allRecords = recordDao.getAllRecords()
        binding.calendarView.notifyCalendarChanged()
        selectedDate?.let {showRecordsForDate(it)}
    }
}