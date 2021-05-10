package io.github.turskyi.pastaandpizzaz

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import io.github.turskyi.pastaandpizzaz.ui.pasta.PastaFragment
import io.github.turskyi.pastaandpizzaz.ui.pizza.PizzaFragment
import io.github.turskyi.pastaandpizzaz.ui.stores.StoresFragment
import io.github.turskyi.pastaandpizzaz.ui.top.TopFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        private const val POSITION = "position"
        private const val VISIBLE_FRAGMENT = "visible_fragment"
    }

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var shareActionProvider: ShareActionProvider
    private lateinit var titles: Array<String>
    private lateinit var drawerList: ListView
    private lateinit var drawerLayout: DrawerLayout
    private var currentPosition = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* Inflate the menu; this adds items to the app bar. */
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider
        "Want to join me for pizza?".setShareActionIntent()
        return super.onCreateOptionsMenu(menu)
    }

    /* Called whenever we call invalidateOptionsMenu() */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        /* If the drawer is open, hide action items related to the content view */
        val drawerOpen = drawerLayout.isDrawerOpen(drawerList)
        menu?.findItem(R.id.action_share)?.isVisible = !drawerOpen
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        /* Code to handle the rest of the action items */
        return when (item.itemId) {
            R.id.action_create_order -> {
                /* Code to run when the Create Order item is clicked */
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        /* Sync the toggle state after onRestoreInstanceState has occurred. */
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(POSITION, currentPosition)
    }

    private var drawerListener: DrawerListener = object : DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            /* Action to onDrawerSlider */
        }

        /* Called when a drawer has settled in a completely open state. */
        override fun onDrawerOpened(drawerView: View) {
            /* Action to onDrawerOpened */
            invalidateOptionsMenu()
        }

        /* Called when a drawer has settled in a completely closed state */
        override fun onDrawerClosed(drawerView: View) {
            /* Action to onDrawerClosed */
            invalidateOptionsMenu()
        }

        override fun onDrawerStateChanged(newState: Int) {
            /* Action to onDrawerStateChanged */
        }
    }

    private fun initListeners() {
        drawerList.onItemClickListener = DrawerItemClickListener()
        drawerLayout.addDrawerListener(drawerListener)
        supportFragmentManager.addOnBackStackChangedListener {
            val fragMan: FragmentManager = supportFragmentManager
            val fragment = fragMan.findFragmentByTag(VISIBLE_FRAGMENT)
            if (fragment is TopFragment) currentPosition = 0
            if (fragment is PizzaFragment) currentPosition = 1
            if (fragment is PastaFragment) currentPosition = 2
            if (fragment is StoresFragment) currentPosition = 3
            setActionBarTitle(currentPosition)
            drawerList.setItemChecked(currentPosition, true)
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        titles = resources.getStringArray(R.array.titles)
        drawerList = findViewById(R.id.drawer)
        drawerLayout = findViewById(R.id.drawer_layout)
        /* Display the correct fragment. */
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(POSITION)
            setActionBarTitle(currentPosition)
        } else {
            selectItem(currentPosition)
        }
        /* Populate the ListView */
        drawerList.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1, titles as Array<out String>
        )
        /* Create the ActionBarDrawerToggle */
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, null,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun String.setShareActionIntent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, this)
        shareActionProvider.setShareIntent(intent)
    }

    private fun selectItem(position: Int) {
        /* update the main content by replacing fragments */
        currentPosition = position
        val fragment: Fragment = when (position) {
            1 -> PizzaFragment()
            2 -> PastaFragment()
            3 -> StoresFragment()
            else -> TopFragment()
        }
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            /* adding a tag of "visible_fragment" to the replace() method. Every fragment that’s
             displayed in MainActivity will be tagged with this value. */
            replace(R.id.content_frame, fragment, VISIBLE_FRAGMENT)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.commit()
        }

        /* Set the action bar title */
        setActionBarTitle(position)

        /* Close the drawer */
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawerLayout.closeDrawer(drawerList)
    }

    private fun setActionBarTitle(position: Int) {
        val title: String = if (position == 0) {
            resources.getString(R.string.app_name)
        } else {
            titles[position]
        }
        supportActionBar?.title = title
    }


    private inner class DrawerItemClickListener : OnItemClickListener {
        override fun onItemClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            /* Code to run when an item in the navigation drawer gets clicked */
            selectItem(position)
        }
    }
}