package com.assettrack.assettrack.Views.Admin.Assets

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.assettrack.assettrack.Constatnts.GLConstants
import com.assettrack.assettrack.Data.PrefManager
import com.assettrack.assettrack.Interfaces.UtilListeners.DrawerItemListener
import com.assettrack.assettrack.Kogi.BottomNav.AHBottomNavigation
import com.assettrack.assettrack.Kogi.BottomNav.AHBottomNavigationItem
import com.assettrack.assettrack.R
import com.assettrack.assettrack.R.id.fab
import com.assettrack.assettrack.Utils.DrawerClass
import com.assettrack.assettrack.Views.Admin.Clients.ActivityManageClients
import com.assettrack.assettrack.Views.Admin.Engineers.ActivityManageEngineers
import com.assettrack.assettrack.Views.Admin.Issues.ActivityManageIssues
import com.assettrack.assettrack.Views.Shared.Login.LoginActivity

import kotlinx.android.synthetic.main.activity_manage_assets.*

class ActivityManageAssets : AppCompatActivity() {

    internal lateinit var bottomNavigation: AHBottomNavigation
    internal var fragment: Fragment? = null


    private fun setDrawerMenu(toolbar: Toolbar) {
        DrawerClass.getDrawer(this@ActivityManageAssets, toolbar, object : DrawerItemListener {
            override fun logOutClicked() {
                val prefManager = PrefManager(this@ActivityManageAssets)
                prefManager.setIsLoggedIn(false, 1)
                startActivity(Intent(this@ActivityManageAssets, LoginActivity::class.java))
                finish()
            }

            override fun helpClicked() {

            }

            override fun settingsClicked() {

            }

            override fun assetClicked() {

            }

            override fun clientsClicked() {

                manageClients()
            }

            override fun engineersClicked() {

                manageEngineers()
            }

            override fun issuesClicked() {

                manageStatus()
            }

            override fun accountClicked() {

            }
        })
    }

    fun manageStatus() {
        startActivity(Intent(this, ActivityManageIssues::class.java))

    }

    fun manageEngineers() {
        startActivity(Intent(this, ActivityManageEngineers::class.java))
    }

    fun manageClients() {
        startActivity(Intent(this, ActivityManageClients::class.java))
    }

    fun manageAssets() {
        startActivity(Intent(this, ActivityManageAssets::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_assets)
        setSupportActionBar(toolbar)
        setDrawerMenu(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fab.hide()
        bottomNav()
        fragment = FragmentBase()
        val bundle = Bundle()
        bundle.putInt("type", 0)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentBase).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()


    }

    private fun bottomNav() {
        bottomNavigation = findViewById(R.id.bottom_navigation)

        val item1 = AHBottomNavigationItem("All"//null);
                , R.drawable.ic_clear_all_black_24dp, R.color.white
        )
        val item2 = AHBottomNavigationItem("Working", //null
                R.drawable.ic_verified_user_black_24dp, R.color.green
        )
        val item3 = AHBottomNavigationItem("Faulty", //null
                R.drawable.ic_error_outline_black_24dp, R.color.red
        )
        val item4 = AHBottomNavigationItem("Service", //null
                R.drawable.ic_phonelink_off_black_24dp, R.color.orange_color_picker
        )

        item1.setColor(resources.getColor(R.color.colorPrimary))
        item2.setColor(resources.getColor(R.color.green_color_picker))
        item3.setColor(resources.getColor(R.color.red))
        item4.setColor(resources.getColor(R.color.orange_color_picker))

        // Add items
        bottomNavigation.addItem(item1)
        bottomNavigation.addItem(item2)
        bottomNavigation.addItem(item3)
        bottomNavigation.addItem(item4)

        bottomNavigation.isColored = true
        bottomNavigation.isSoundEffectsEnabled = true
        bottomNavigation.isBehaviorTranslationEnabled = true
        bottomNavigation.currentItem = 0
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"))
        bottomNavigation.setOnTabSelectedListener { position, wasSelected ->
            val bundle = Bundle()
            fragment = FragmentBase()
            when (position) {
                0 -> {

                    popOutFragments()
                    bundle.putInt("type", 0)
                    bundle.putInt("STATUS_ID", 0)
                    fragment?.arguments = bundle
                    setFragment()
                }
                1 -> {
                    popOutFragments()
                    bundle.putInt("type", 1)
                    bundle.putInt("STATUS_ID", GLConstants.WORKING)
                    fragment?.arguments = bundle
                    setFragment()
                }
                2 -> {
                    popOutFragments()
                    bundle.putInt("type", 2)
                    bundle.putInt("STATUS_ID", GLConstants.FAULTY)
                    fragment?.arguments = bundle
                    setFragment()
                }

                3 -> {
                    popOutFragments()
                    bundle.putInt("type", 3)
                    bundle.putInt("STATUS_ID", GLConstants.WRITTEN_OFF)
                    fragment?.arguments = bundle
                    setFragment()
                }
            }
            true
        }
        bottomNavigation.setOnNavigationPositionListener { y ->
            // Manage the new y position
        }

        try {
            setCount(0, 0)
        } catch (nm: Exception) {
            nm.printStackTrace()
        }

        setCount(2, 0)
        setCount(6, 1)
        setCount(1, 2)
        setCount(6, 3)

    }


    fun setCount(count: Int, pos: Int) {
        try {

            if (count > 0) {

                bottomNavigation.setNotification(count.toString(), pos)
            } else {
                bottomNavigation.setNotification("", pos)
            }
        } catch (nm: Exception) {
            nm.printStackTrace()
        }

    }

    internal fun setFragment() {
        // fragment = new FragmentSearch();
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()
    }

    internal fun popOutFragments() {
        val fragmentManager = this.supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }
}
