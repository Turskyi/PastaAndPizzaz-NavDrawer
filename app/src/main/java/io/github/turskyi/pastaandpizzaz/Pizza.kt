package io.github.turskyi.pastaandpizzaz

data class Pizza(
    val name: String,
    val imageResourceId: Int
) {
    // Imitating retrieving objects from datasource.
    companion object {
        val pizzas = arrayOf(
            Pizza("Diavolo", R.drawable.pic_diavolo),
            Pizza("Funghi", R.drawable.pic_funghi)
        )
    }
}