package ddwu.com.mobile.foodexam

data class FoodDto(var food: String, var country: String) {
    override fun toString() = "$food ($country)"
}

