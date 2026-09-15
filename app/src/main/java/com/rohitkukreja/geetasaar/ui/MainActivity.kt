package com.rohitkukreja.geetasaar.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rohitkukreja.geetasaar.R
import com.rohitkukreja.geetasaar.databinding.ActivityMainBinding
import com.rohitkukreja.geetasaar.ui.aartis.AartisFragment
import com.rohitkukreja.geetasaar.ui.chapters.ChaptersFragment
import com.rohitkukreja.geetasaar.ui.favorites.FavoritesFragment
import com.rohitkukreja.geetasaar.ui.mantras.MantrasFragment
import com.rohitkukreja.geetasaar.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            showFragment(ChaptersFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_gita -> ChaptersFragment()
                R.id.nav_mantras -> MantrasFragment()
                R.id.nav_aartis -> AartisFragment()
                R.id.nav_favorites -> FavoritesFragment()
                else -> ChaptersFragment()
            }
            showFragment(fragment)
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
