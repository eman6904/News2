package com.example.news2

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.news2.data.viewModels.SourcesViewModel
import com.example.news2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val sourcesViewModel: SourcesViewModel by viewModels()
    companion object{
        val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
        lateinit var appLanguage: SharedPreferences
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appLanguage = PreferenceManager.getDefaultSharedPreferences(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host)

        val languageCode = getLanguageFromPreferences()
        setAppLocale(languageCode)

        setSupportActionBar(binding.categoryToolbar)
        setNavigationDrawerMenu(
            navController =  navController,
            binding =  binding,
            activity =  this,
            toolbar =  binding.categoryToolbar,
            sourcesViewModel = sourcesViewModel
        )

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            Log.d("BackStackCheck", "Navigated to: ${destination.label}")
//        }

    }
    private fun getLanguageFromPreferences(): String {

        return appLanguage.getString(SELECTED_LANGUAGE, null)?:"en"
    }
    private fun setAppLocale(languageCode:String){

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

private fun setNavigationDrawerMenu(
    navController: NavController,
    binding:ActivityMainBinding,
    activity: MainActivity,
    toolbar:androidx.appcompat.widget.Toolbar,
    sourcesViewModel: SourcesViewModel

    ){

    val toggle = ActionBarDrawerToggle(
        activity, binding.drawerLayout, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close
    )
    binding.drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
    //to change toolbar icon color
    val icon = toolbar.navigationIcon
    icon?.setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.SRC_ATOP)

    binding.navigationView.setNavigationItemSelectedListener { menuItem ->

        when (menuItem.itemId) {
            R.id.nav_categories -> {

                if(sourcesViewModel.currentFragment.value != R.id.nav_categories) {
                    navController.navigate(R.id.categoriesFragment)
                    sourcesViewModel.setCurrentFragment(R.id.nav_categories)
                }
            }
            R.id.nav_settings -> {

                if(sourcesViewModel.currentFragment.value != R.id.nav_settings) {
                    navController.navigate(R.id.settingFragment)
                    sourcesViewModel.setCurrentFragment(R.id.nav_settings)
                }

            }
        }
        binding.drawerLayout.closeDrawers()
        true
    }
    binding.navigationView.menu.findItem(R.id.nav_categories).title = activity.getString(R.string.categories)
    binding.navigationView.menu.findItem(R.id.nav_settings).title = activity.getString(R.string.settings)


}