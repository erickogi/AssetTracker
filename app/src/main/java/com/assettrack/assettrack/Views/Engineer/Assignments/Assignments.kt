package com.assettrack.assettrack.Views.Engineer.Assignments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.assettrack.assettrack.Models.AssetModel
import com.assettrack.assettrack.Models.EngineerModel
import com.assettrack.assettrack.R


class Assignments : AppCompatActivity() {
    internal var fragment: Fragment? = null
    internal var fragmentIssueList: FragmentIssueList? = null
    internal lateinit var mStaggeredLayoutManager: StaggeredGridLayoutManager
    internal var engineerModel: EngineerModel? = null
    internal var assetModel: AssetModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignments)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        fab.hide()

        fragment = FragmentIssueList()
        val bundle = Bundle()
        bundle.putInt("type", 1)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentIssueList).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()

    }

}
