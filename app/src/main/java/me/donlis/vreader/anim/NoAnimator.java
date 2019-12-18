package me.donlis.vreader.anim;


import android.os.Parcel;
import android.os.Parcelable;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class NoAnimator extends FragmentAnimator implements Parcelable {
    public NoAnimator() {
        enter = 0;
        exit = 0;
        popEnter = 0;
        popExit = 0;
    }

    protected NoAnimator(Parcel in) {
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

    public static final Creator<NoAnimator> CREATOR = new Creator<NoAnimator>() {
        @Override
        public NoAnimator createFromParcel(Parcel in) {
            return new NoAnimator(in);
        }

        @Override
        public NoAnimator[] newArray(int size) {
            return new NoAnimator[size];
        }
    };
}
