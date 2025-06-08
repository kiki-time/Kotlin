package kikicom.mobile.forensictil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kikicom.mobile.forensictil.data.ForensicRecord
import kikicom.mobile.forensictil.databinding.ItemRecordBinding

class RecordAdapter(
    private val records: List<ForensicRecord>,
    private val onItemClick: (ForensicRecord) -> Unit,
    private val onItemLongClick: (ForensicRecord) -> Unit )
    : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(private val binding: ItemRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: ForensicRecord) {
            binding.tvDate.text = record.date
            binding.tvTopic.text = "주제: ${record.topic}"
            binding.tvTool.text = "도구: ${record.tool}"
            binding.tvTime.text = "공부 시간: ${record.timeSpent}분"

            binding.root.setOnClickListener {
                onItemClick(record)
            }

            binding.root.setOnLongClickListener{
                onItemLongClick(record)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecordViewHolder,
        position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int = records.size
}