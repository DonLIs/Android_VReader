package me.donlis.vreader.anim;

import android.os.Parcel;
import android.os.Parcelable;

import me.donlis.vreader.R;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


public class VerticalAnimator extends FragmentAnimator implements Parcelable{

    public VerticalAnimator() {
        enter = R.anim.v_fragment_enter;
        exit = R.anim.v_fragment_exit;
        popEnter = R.anim.v_fragment_pop_enter;
        popExit = R.anim.v_fragment_pop_exit;
    }

    protected VerticalAnimator(Parcel in) {
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

    public static final Creator<VerticalAnimator> CREATOR = new Creator<VerticalAnimator>() {
        @Override
        public VerticalAnimator createFromParcel(Parcel in) {
            return new VerticalAnimator(in);
        }

        @Override
        public VerticalAnimator[] newArray(int size) {
            return new VerticalAnimator[size];
        }
    };
}
