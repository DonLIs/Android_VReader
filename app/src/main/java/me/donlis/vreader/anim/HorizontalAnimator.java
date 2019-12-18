package me.donlis.vreader.anim;

import android.os.Parcel;
import android.os.Parcelable;

import me.donlis.vreader.R;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class HorizontalAnimator extends FragmentAnimator implements Parcelable{

    public HorizontalAnimator() {
        enter = R.anim.h_fragment_enter;
        exit = R.anim.h_fragment_exit;
        popEnter = R.anim.h_fragment_pop_enter;
        popExit = R.anim.h_fragment_pop_exit;
    }

    protected HorizontalAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HorizontalAnimator> CREATOR = new Creator<HorizontalAnimator>() {
        @Override
        public HorizontalAnimator createFromParcel(Parcel in) {
            return new HorizontalAnimator(in);
        }

        @Override
        public HorizontalAnimator[] newArray(int size) {
            return new HorizontalAnimator[size];
        }
    };
}
