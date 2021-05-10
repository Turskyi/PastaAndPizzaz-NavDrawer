package io.github.turskyi.pastaandpizzaz.ui.pizza

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import io.github.turskyi.pastaandpizzaz.Pizza
import io.github.turskyi.pastaandpizzaz.R
import io.github.turskyi.pastaandpizzaz.ui.OrderActivity

class PizzaDetailActivity : AppCompatActivity(R.layout.activity_pizza_detail) {
    companion object {
        const val EXTRA_PIZZA_ID = "pizzaId"
    }
    private lateinit var shareActionProvider: ShareActionProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Enables the Up button
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        /* Displays details of the pizza */
        val pizzaId:Int = intent.extras?.get(EXTRA_PIZZA_ID) as Int
        val pizzaName:String = Pizza.pizzas[pizzaId].name
        val textView:TextView = findViewById(R.id.pizza_text)
        textView.text = pizzaName
        val pizzaImage:Int = Pizza.pizzas[pizzaId].imageResourceId
        textView.setCompoundDrawablesWithIntrinsicBounds(0, pizzaImage, 0, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        //Share the name of the pizza
        val textView = findViewById<View>(R.id.pizza_text) as TextView
        val pizzaName = textView.text
        val menuItem: MenuItem = menu.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, pizzaName)
        shareActionProvider.setShareIntent(intent)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_order -> {
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}