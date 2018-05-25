package com.assettrack.assettrack.Views.Shared.Asset;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;
import com.assettrack.assettrack.Utils.DateTimeUtils;
import com.fueled.fabulous.FabulousPattern;
import com.fueled.fabulous.FabulousPosition;
import com.nightonke.boommenu.BoomMenuButton;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class AssetActivity extends AppCompatActivity {

    public AssetModel asset;
    public boolean editable;
    ViewPagerAdapter adapter;
    String id;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SwipeRefreshLayout swipe_refresh_layout;
    private FragmentAssetDetails fragmentDetails;
    private FragmentAssetIssues fragmentIssues;
    private FragmentChartView fragmentChartView;
    private FloatingActionButton fab;
    private BoomMenuButton bmb;

//    void setBmb() {
//        bmb = findViewById(R.id.bmb);
//        bmb.setNormalColor(R.color.colorAccent);
//        bmb.setRippleEffect(true);
//
//        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
//        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
//
//        HamButton.Builder buildero = new HamButton.Builder()
//
//                .listener(new OnBMClickListener() {
//                    @Override
//                    public void onBoomButtonClick(int index) {
//
//
//                    }
//                })
//                // .button().setClickable(true)
//                .normalImageRes(R.drawable.code_bar)
//                .normalText("Add New Asset")
//                .containsSubText(true)
//                .rippleEffect(true)
//                .rotateImage(true)
//
//                .subNormalText("Add a new asset to be able to track and manage");
//        bmb.addBuilder(buildero);
//
//
//        //for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
//        HamButton.Builder builder = new HamButton.Builder()
//
//                .listener(new OnBMClickListener() {
//                    @Override
//                    public void onBoomButtonClick(int index) {
//
//
//                    }
//                })
//                // .button().setClickable(true)
//                .normalImageRes(R.drawable.code_bar)
//                .normalText("Scan Bar code")
//                .containsSubText(true)
//                .rippleEffect(true)
//                .rotateImage(true)
//
//                .subNormalText("Find asset by scanning a bar code");
//        bmb.addBuilder(builder);
//
//
//        HamButton.Builder builder1 = new HamButton.Builder()
//                .normalImageRes(R.drawable.code)
//                .containsSubText(true)
//                .rippleEffect(true)
//                .normalText("Enter Asset Code")
//                .rotateImage(true)
//                .listener(new OnBMClickListener() {
//                    @Override
//                    public void onBoomButtonClick(int index) {
//
//                    }
//                })
//                //.rotateImage(true)
//
//
//                .subNormalText("Find asset by its unique code");
//
//
//        bmb.addBuilder(builder1);
//
//        HamButton.Builder builder2 = new HamButton.Builder()
//                .normalImageRes(R.drawable.ic_search_black_24dp)
//                .normalText("Assets List")
//                .containsSubText(true)
//                .rippleEffect(true)
//                .subNormalText("List all assets registered")
//                .listener(new OnBMClickListener() {
//                    @Override
//                    public void onBoomButtonClick(int index) {
//
//
//                    }
//                });
//        bmb.addBuilder(builder2);
//        bmb.setAutoBoomImmediately(false);
//
//        bmb.setOnBoomListener(new OnBoomListener() {
//            @Override
//            public void onClicked(int index, BoomButton boomButton) {
//                // startScan();
//            }
//
//            @Override
//            public void onBackgroundClick() {
//
//                // img_fab.setRotation(45);
//                bmb.setAutoBoomImmediately(true);
//            }
//
//            @Override
//            public void onBoomWillHide() {
//                // img_fab.setRotation(45);
//                bmb.setAutoHide(true);
//            }
//
//            @Override
//            public void onBoomDidHide() {
//
//            }
//
//            @Override
//            public void onBoomWillShow() {
//                // img_fab.setRotation(45);
//            }
//
//            @Override
//            public void onBoomDidShow() {
//
//            }
//        });
//        //}
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        fab.setVisibility(View.GONE);
        //  setBmb();

//        new Fabulous.Builder(this)
//                .setFab(fab)
//
//                .setMenuId(R.menu.menu_asset_options)
//                .setMenuPattern(new LinearPattern())
//                .build();
//

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        setupTabIcons();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getSerializableExtra("data") != null) {
                asset = (AssetModel) intent.getSerializableExtra("data");
                editable = intent.getBooleanExtra("state", false);

            }
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        fragmentDetails = new FragmentAssetDetails();
        fragmentIssues = new FragmentAssetIssues();
        ///fragmentChartView=new FragmentChartView();

        Bundle args = new Bundle();
        args.putString("key_id", id);
        fragmentIssues.setArguments(args);
        fragmentDetails.setArguments(args);

        adapter.addFragment(fragmentDetails, "lo");
        adapter.addFragment(fragmentIssues, "li");
        //adapter.addFragment(fragmentChartView,"");

        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.black));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("Asset Details");
        tabLayout.getTabAt(1).setText("Asset History");
        //tabLayout.getTabAt(2).setText("Chart");


    }

    private void startDialog() {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(AssetActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_new_issue, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(AssetActivity.this);
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("New  Issue");
        alertDialogBuilderUserInput.setIcon(R.drawable.ic_add_black_24dp);
        TextView txtDate, txtBy;
        txtBy = mView.findViewById(R.id.txt_by);
        txtDate = mView.findViewById(R.id.txt_date);

        txtBy.setText("Eric Kogi");
        txtDate.setText(DateTimeUtils.Companion.getToday());


        // final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Save", (dialogBox, id) -> {
                    // ToDo get user input here

                    // dialogBox.dismiss();

                })

                .setNegativeButton("Dismiss",
                        (dialogBox, id) -> dialogBox.cancel());

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
        Button theButton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new CustomListener(alertDialogAndroid));

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new LinkedList<>();
        private final List<String> mFragmentTitleList = new LinkedList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;


        public CustomListener(Dialog dialog) {
            this.dialog = dialog;

        }

        @Override
        public void onClick(View v) {
            EditText edtIssue, edtIssueDesc, edtFix, edtComment;
            TextView txtDate, txtBy;

            edtIssue = dialog.findViewById(R.id.edt_issue_title);
            edtIssueDesc = dialog.findViewById(R.id.edt_issue_desc);
            edtFix = dialog.findViewById(R.id.edt_fix);
            edtComment = dialog.findViewById(R.id.edt_comment);

            txtBy = dialog.findViewById(R.id.txt_by);
            txtDate = dialog.findViewById(R.id.txt_date);

            if (edtIssue.getText().toString().isEmpty()) {
                edtIssue.setError("Required");
                return;
            }
            if (edtComment.getText().toString().isEmpty()) {
                edtComment.setError("Required");
                return;
            }
            if (edtFix.getText().toString().isEmpty()) {
                edtFix.setError("Required");
                return;
            }

            if (edtIssueDesc.getText().toString().isEmpty()) {
                edtIssue.setError("Required");
                return;
            }

            IssueModel issues = new IssueModel();
            issues.setAssets_id(String.valueOf(asset.getId()));
            issues.setAsset_code(asset.getAsset_code());
            //issues.se(DateTimeUtils.Companion.getToday());
            issues.setFailure_soln(edtFix.getText().toString());
            issues.setFailure_desc(edtIssueDesc.getText().toString());
            issues.setEngineer_comment(edtIssue.getText().toString());
            //   issues.setEngineer_id();


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar.make(fab, item.getTitle(), Snackbar.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public class LinearPattern implements FabulousPattern {
        private static final int MAIN_FAB_ANIMATION_DURATION = 200;

        @NotNull
        @Override
        public AnimatorSet getClosingAnimation(@NotNull View element, float destX, float destY) {
            AnimatorSet anim = new AnimatorSet();
            ObjectAnimator fabX = ObjectAnimator.ofFloat(element, View.X, destX);
            fabX.setDuration(MAIN_FAB_ANIMATION_DURATION);
            ObjectAnimator fabY = ObjectAnimator.ofFloat(element, View.Y, destY);
            fabY.setDuration(MAIN_FAB_ANIMATION_DURATION);

            anim.play(fabX).with(fabY);
            return anim;
        }

        @NotNull
        @Override
        public AnimatorSet getOpeningAnimation(@NotNull View element, float destX, float destY) {
            AnimatorSet anim = new AnimatorSet();
            ObjectAnimator fabX = ObjectAnimator.ofFloat(element, View.X, destX);
            fabX.setDuration(MAIN_FAB_ANIMATION_DURATION);
            ObjectAnimator fabY = ObjectAnimator.ofFloat(element, View.Y, destY);
            fabY.setDuration(MAIN_FAB_ANIMATION_DURATION);

            anim.play(fabX).with(fabY);
            return anim;
        }

        @NotNull
        @Override
        public FabulousPosition getFinalPosition(@NotNull View subMenu, float fabX, float fabY, int position, int menuSize) {
            return new FabulousPosition(fabX, fabY - ((position + 1) * 200));
        }
    }
}
