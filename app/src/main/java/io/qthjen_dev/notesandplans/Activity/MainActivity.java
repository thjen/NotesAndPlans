package io.qthjen_dev.notesandplans.Activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.qthjen_dev.notesandplans.Adapter.MyPagerAdapter;
import io.qthjen_dev.notesandplans.Database.MySQLite;
import io.qthjen_dev.notesandplans.R;
import io.qthjen_dev.notesandplans.Utils.NotesDatabaseManager;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTbar;
    private ViewPager mViewPager;
    /** custom toolbar **/
    private LinearLayout mBtNotes, mBtPlans;
    private TextView mTvNote, mTvPlan, mTitleTbar;
    private ImageView mIvNote, mIvPlan;
    /**--------------------------------**/

    private MyPagerAdapter mAdapter;
    //private MySQLite mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();

    }

    private void InitView() {

        /** create table database **/
        //mDatabase = new MySQLite(this, "noteandplan.sqlite",null, 1);
        //mDatabase.QueryData("CREATE TABLE IF NOT EXISTS Notes(id INTEGER PRIMARY KEY AUTOINCREMENT, description VARCHAR, time VARCHAR, color VARCHAR, image BLOB)");

        /** custom toolbar **/
        mTbar = findViewById(R.id.tbar_main);
        setSupportActionBar(mTbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.home));
        //actionBar.setElevation(getResources().getDimension(R.dimen.smallElevation));
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionbarView = inflater.inflate(R.layout.custom_tbarmain, null);

        mBtNotes = actionbarView.findViewById(R.id.bt_note);
        mBtPlans = actionbarView.findViewById(R.id.bt_plan);
        mTvNote = actionbarView.findViewById(R.id.tv_note);
        mTvPlan = actionbarView.findViewById(R.id.tv_plan);
        mIvNote = actionbarView.findViewById(R.id.iv_note);
        mIvPlan = actionbarView.findViewById(R.id.iv_plan);
        mTitleTbar = actionbarView.findViewById(R.id.title_tbar);

        actionBar.setCustomView(actionbarView);

        /** set adapter **/
        mViewPager = findViewById(R.id.mainviewpager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        /** event swipe pager **/
        mViewPager.setCurrentItem(0);
        mTitleTbar.setText(getResources().getString(R.string.note));
        mBtNotes.setBackground(getResources().getDrawable(R.drawable.notepager_isselected));
        mBtPlans.setBackground(getResources().getDrawable(R.drawable.planpager_unselected));
        mTvNote.setTextColor(getResources().getColor(R.color.textWhite));
        mTvPlan.setTextColor(getResources().getColor(R.color.btswipepager));
        mIvNote.setImageDrawable(getResources().getDrawable(R.drawable.notewhite));
        mIvPlan.setImageDrawable(getResources().getDrawable(R.drawable.plansvg));

        mBtNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(0);

            }
        });

        mBtPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(1);

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if ( position == 0 ) {

                    mTitleTbar.setText(getResources().getString(R.string.note));
                    mBtNotes.setBackground(getResources().getDrawable(R.drawable.notepager_isselected));
                    mBtPlans.setBackground(getResources().getDrawable(R.drawable.planpager_unselected));
                    mTvNote.setTextColor(getResources().getColor(R.color.textWhite));
                    mTvPlan.setTextColor(getResources().getColor(R.color.btswipepager));
                    mIvNote.setImageDrawable(getResources().getDrawable(R.drawable.notewhite));
                    mIvPlan.setImageDrawable(getResources().getDrawable(R.drawable.plansvg));

                } else if ( position == 1 ) {

                    mTitleTbar.setText(getResources().getString(R.string.plan));
                    mBtNotes.setBackground(getResources().getDrawable(R.drawable.notepager_unselected));
                    mBtPlans.setBackground(getResources().getDrawable(R.drawable.planpager_isselected));
                    mTvNote.setTextColor(getResources().getColor(R.color.btswipepager));
                    mTvPlan.setTextColor(getResources().getColor(R.color.textWhite));
                    mIvNote.setImageDrawable(getResources().getDrawable(R.drawable.notesvg));
                    mIvPlan.setImageDrawable(getResources().getDrawable(R.drawable.planwhite));

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
