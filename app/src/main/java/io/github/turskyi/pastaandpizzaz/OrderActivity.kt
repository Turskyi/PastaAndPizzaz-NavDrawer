package io.github.turskyi.pastaandpizzaz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity(R.layout.activity_order) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /* on click is this way because it is called from layout */
    fun onClickDone(view: View?) {
        val text: CharSequence = "Your order has been updated"
        val duration = Snackbar.LENGTH_SHORT
        val snackbar =
            Snackbar.make(findViewById(R.id.coordinator), text, duration)
        snackbar.setAction(
            "Undo"
        ) {
            val toast = Toast.makeText(
                this@OrderActivity, "Undone!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
        snackbar.show()
    }
}