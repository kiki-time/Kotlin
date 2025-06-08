package kikicom.mobile.forensictil.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kikicom.mobile.forensictil.dao.RecordDao
import kikicom.mobile.forensictil.data.ForensicRecord
import kikicom.mobile.forensictil.databinding.ActivityUpdateRecordBinding

class UpdateRecordActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityUpdateRecordBinding
    private lateinit var recordDao: RecordDao
    private lateinit var record: ForensicRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="기록 수정"

        recordDao = RecordDao(this)

        record = intent.getSerializableExtra("record") as ForensicRecord

        binding.etUpdateDate.setText(record.date)
        binding.etUpdateTopic.setText(record.topic)
        binding.etUpdateTool.setText(record.tool)
        binding.etUpdateContent.setText(record.content)
        binding.etUpdateTimeSpent.setText(record.timeSpent.toString())

        binding.btnUpdate.setOnClickListener {
            val updated = record.copy(
                date = binding.etUpdateDate.text.toString().trim(),
                topic = binding.etUpdateTopic.text.toString().trim(),
                tool = binding.etUpdateTool.text.toString().trim(),
                content = binding.etUpdateContent.text.toString().trim(),
                timeSpent = binding.etUpdateTimeSpent.text.toString().toIntOrNull() ?: 0
            )
            val result = recordDao.updateRecord(updated)
            if(result > 0) {
                Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}