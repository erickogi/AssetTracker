package com.assettrack.assettrack.Views.Admin.Clients

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.assettrack.assettrack.R
import kotlinx.android.synthetic.main.activity_manage_clients.*

class ActivityManageClients : AppCompatActivity() {
    internal var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_clients)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fragment = FragmentClientList()
        val bundle = Bundle()
        bundle.putInt("type", 0)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentClientList).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()


    }

}
