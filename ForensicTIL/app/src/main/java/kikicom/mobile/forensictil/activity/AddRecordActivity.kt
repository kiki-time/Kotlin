package kikicom.mobile.forensictil.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kikicom.mobile.forensictil.dao.RecordDao
import kikicom.mobile.forensictil.data.ForensicRecord
import kikicom.mobile.forensictil.databinding.ActivityAddRecordBinding

class AddRecordActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding
    private lateinit var recordDao: RecordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recordDao = RecordDao(this)

        binding.btnSave.setOnClickListener {
            val date = binding.etDate.text.toString().trim()
            val topic = binding.etTopic.text.toString().trim()
            val tool = binding.etTool.text.toString().trim()
            val content = binding.etContent.text.toString().trim()
            val timeSpent = binding.etTimeSpent.text.toString().trim()

            if (date.isEmpty() || topic.isEmpty() ||
                tool.isEmpty() || content.isEmpty() || timeSpent.isEmpty()) {
                Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newRecord = ForensicRecord(
                date = date,
                topic = topic,
                tool = tool,
                content = content,
                timeSpent = timeSpent.toInt()
            )
            recordDao.insertRecord(newRecord)
            Toast.makeText(this, "학습 기록이 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}