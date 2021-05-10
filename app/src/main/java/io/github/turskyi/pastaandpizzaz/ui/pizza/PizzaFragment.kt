package io.github.turskyi.pastaandpizzaz.ui.pizza

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.turskyi.pastaandpizzaz.Pizza
import io.github.turskyi.pastaandpizzaz.R

/**
 * A simple [PizzaFragment] subclass.
 */
class PizzaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pizzaRecycler: RecyclerView = inflater.inflate(
            R.layout.fragment_pizza, container, false
        ) as RecyclerView
        val pizzaNames: Array<String?> = arrayOfNulls(Pizza.pizzas.size)
        for (i in pizzaNames.indices) {
            pizzaNames[i] = Pizza.pizzas[i].name
        }
        val pizzaImages = IntArray(Pizza.pizzas.size)
        for (i in pizzaImages.indices) {
            pizzaImages[i] = Pizza.pizzas[i].imageResourceId
        }

        val adapter = CaptionedImagesAdapter(pizzaNames, pizzaImages)
        pizzaRecycler.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        pizzaRecycler.layoutManager = layoutManager

        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                val intent = Intent(activity, PizzaDetailActivity::class.java)
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZA_ID, position)
                requireActivity().startActivity(intent)
            }
        })
        return pizzaRecycler
    }
}