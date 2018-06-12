package com.assettrack.assettrack.Views.Admin.Engineers

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.assettrack.assettrack.R

class ActivityManageEngineers : AppCompatActivity() {
    internal var fragment: Fragment? = null
    var  fab: FloatingActionButton?=null
    var  toolbar: android.support.v7.widget.Toolbar?=null
    fun popOut() {
        //
        popOutFragments()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_engineers)

        toolbar=findViewById(R.id.toolbar)
        fab=findViewById(R.id.fab)
        setSupportActionBar(toolbar)

        fab?.setOnClickListener { view ->
            fragment= FragmentEdit()
            // popOutFragments()
            setFragment()
        }

        fragment = FragmentEngineerList()
        val bundle = Bundle()
        bundle.putInt("type", 0)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentEngineerList).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()

    }

    fun setFab(icon: Int, isVisible: Boolean) {
        fab?.setImageResource(icon)
        if(isVisible){
            fab?.show()
        }else{
            fab?.hide()
        }
    }


    internal fun setFragment() {
        // fragment = new FragmentSearch();
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").addToBackStack(null).commit()
    }

    fun popOutFragments() {
        val fragmentManager = this.supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

}
