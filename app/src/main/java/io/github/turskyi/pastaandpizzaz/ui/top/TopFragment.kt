package io.github.turskyi.pastaandpizzaz.ui.top

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.turskyi.pastaandpizzaz.Pizza
import io.github.turskyi.pastaandpizzaz.R
import io.github.turskyi.pastaandpizzaz.ui.pizza.CaptionedImagesAdapter
import io.github.turskyi.pastaandpizzaz.ui.pizza.PizzaDetailActivity


class TopFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout: RelativeLayout = inflater.inflate(
            R.layout.fragment_top,
            container,
            false
        ) as RelativeLayout
        val pizzaRecycler =
            layout.findViewById<View>(R.id.pizza_recycler) as RecyclerView
        val pizzaNames = arrayOfNulls<String>(2)
        for (i in 0..1) {
            pizzaNames[i] = Pizza.pizzas[i].name
        }
        val pizzaImages = IntArray(2)
        for (i in 0..1) {
            pizzaImages[i] = Pizza.pizzas[i].imageResourceId
        }
        val layoutManager = GridLayoutManager(activity, 2)
        pizzaRecycler.layoutManager = layoutManager
        val adapter = CaptionedImagesAdapter(pizzaNames, pizzaImages)
        pizzaRecycler.adapter = adapter
        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                val intent = Intent(activity, PizzaDetailActivity::class.java)
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZA_ID, position)
                requireActivity().startActivity(intent)
            }
        })
        return layout
    }
}
