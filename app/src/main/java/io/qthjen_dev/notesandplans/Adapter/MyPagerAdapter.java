package io.qthjen_dev.notesandplans.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.qthjen_dev.notesandplans.Fragment.NotesFragment;
import io.qthjen_dev.notesandplans.Fragment.PlansFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if ( position == 0 ) {

            NotesFragment notesFragment = new NotesFragment();
            return notesFragment;

        } else if ( position == 1 ) {

            PlansFragment plansFragment = new PlansFragment();
            return plansFragment;

        } else {

            return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }

}
