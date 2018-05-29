package com.assettrack.assettrack.Utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.Toolbar
import com.assettrack.assettrack.Interfaces.UtilListeners.DrawerItemListener
import com.assettrack.assettrack.R
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem

class DrawerClass {

    private fun getBitmap(activity: Activity, img: String): Bitmap {


        val thumnail = BitmapFactory.decodeResource(activity.resources, R.drawable.ic_person_black_24dp)

        try {

            return thumnail

        } catch (nm: Exception) {
            return thumnail
        }


    }
    companion object {

        fun getDrawer(activity: Activity, toolbar: Toolbar, itemListener: DrawerItemListener) {
             lateinit var result: Drawer
            //private val activity: Activity? = null

            //if you want to update the items at a later time it is recommended to keep it in a variable
            //Bitmap image = getBitmap(activity, img);
            val drawerEmptyItem = PrimaryDrawerItem().withIdentifier(0).withName("")
            drawerEmptyItem.withEnabled(false)

            //  Typeface typeface=getTypeface(activity.getApplicationContext());


            val assets = PrimaryDrawerItem().withIdentifier(1)
                    .withName("Manage Assets").withTextColorRes(R.color.drawertext)
                    .withIcon(R.drawable.assetmanager)


            val clients = PrimaryDrawerItem().withIdentifier(2)
                    .withName("Manage Clients").withTextColorRes(R.color.drawertext).withIcon(R.drawable.clients)
            val engineers = PrimaryDrawerItem().withIdentifier(3)
                    .withName("Manage Engineers").withTextColorRes(R.color.drawertext).withIcon(R.drawable.engineer)
            //        PrimaryDrawerItem favorites = new PrimaryDrawerItem().withIdentifier(4)
            //                .withName("Favorites").withIcon(R.drawable.ic_favorite_black_24dp)
            //                .withBadge("4");


            val issues = PrimaryDrawerItem().withIdentifier(4)
                    .withName("Manage Issues").withTextColorRes(R.color.drawertext).withIcon(R.drawable.ic_error_outline_black_24dp)

            val settings = PrimaryDrawerItem().withIdentifier(5)
                    //.withIcon(R.drawable.ic_settings_black_24dp)
                    .withName("Settings").withTextColorRes(R.color.drawertext)


            val logout = SecondaryDrawerItem().withIdentifier(6)
                    .withName("Log Out").withTextColorRes(R.color.drawertext)//.withIcon(R.drawable.ic_exit_to_app_black_24dp);


            val account = SecondaryDrawerItem().withIdentifier(7)
                    .withName("Account").withTextColorRes(R.color.drawertext)//.withIcon(R.drawable.ic_account_circle_black_24dp);


            val help = PrimaryDrawerItem().withIdentifier(8)
                    .withName("Help").withTextColorRes(R.color.drawertext)


            val headerResult = AccountHeaderBuilder()
                    .withActivity(activity)
                    .withOnProfileClickDrawerCloseDelay(2)
                    .withTextColorRes(R.color.drawertext)
                    .withSelectionListEnabledForSingleProfile(false)
                    .withDividerBelowHeader(true)

                    // .withHeaderBackground(R.drawable.headermain)
                    //                .addProfiles(
                    //                        new ProfileDrawerItem().withName(userModel.getUserName()).withEmail(userModel.getEmail())
                    //
                    //                                //.withSelectedTextColorRes(R.color.colorPrimaryDark)
                    //                                .withIcon(getBitmap(activity, userModel.getPhoto()))
                    //
                    //
                    //                )
                    .withOnAccountHeaderListener { view, profile, currentProfile -> false }
                    .build()


            result = DrawerBuilder()

                    .withActivity(activity)
                    //.withFooter(R.layout.footer)

                    //.withGenerateMiniDrawer(true)
                    .withFooterDivider(false)

                    .withSelectedItem(1)

                    .withToolbar(toolbar)
                    // .withSliderBackgroundDrawableRes(R.drawable.headermain)

                    // .withAccountHeader(headerResult)
                    .withActionBarDrawerToggle(true)
                    .withActionBarDrawerToggleAnimated(true)
                    .withCloseOnClick(true)
                    //.withSelectedItem(-1)
                    .addDrawerItems(
                            // favorites,
                            assets, // payments, //new DividerDrawerItem(),
                            //messages,
                            clients,
                            engineers,
                            issues,
                            DividerDrawerItem(),
                            // account,


                            // settings,

                            logout,
                            // invalidate,
                            DividerDrawerItem()
                            //logout,
                            //share
                            //help
                            // about


                    )
                    .withFooterClickable(true)
                    //.withStickyFooter(R.layout.footer)


                    .withOnDrawerItemClickListener { view, position, drawerItem ->

                        when (drawerItem.identifier.toInt()) {
                            1 -> {
                                itemListener.assetClicked()
                                result.closeDrawer()
                            }

                            2 -> {
                                itemListener.clientsClicked()
                                result.closeDrawer()
                            }

                            3 -> {
                                itemListener.engineersClicked()
                                result.closeDrawer()
                            }
                            4 -> {
                                itemListener.issuesClicked()
                                result.closeDrawer()
                            }
                            5 -> {
                                itemListener.settingsClicked()
                                result.closeDrawer()
                            }
                            6 -> {
                                itemListener.logOutClicked()
                                result.closeDrawer()
                            }

                            7 -> {
                                itemListener.accountClicked()
                                result.closeDrawer()
                            }

                            8 -> {
                                itemListener.helpClicked()
                                result.closeDrawer()
                            }
                        }
                        true
                    }

                    .build()
        }
    }
}