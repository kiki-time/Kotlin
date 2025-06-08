package kikicom.mobile.forensictil.data

import java.io.Serializable

data class ForensicRecord(
    var id: Int = 0,
    var date: String = "", // 학습날짜
    var topic: String = "", // 학습 주제
    var tool: String = "", // 사용 도구
    var content: String = "",  // 학습 내용
    var timeSpent: Int = 0 // 학습한 시간
) : Serializable