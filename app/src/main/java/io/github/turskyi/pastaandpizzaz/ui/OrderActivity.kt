package io.github.turskyi.pastaandpizzaz.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.github.turskyi.pastaandpizzaz.R
import io.github.turskyi.pastaandpizzaz.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
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