package me.donlis.vreader.anim;

import android.os.Parcel;
import android.os.Parcelable;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class FadeAnimator extends FragmentAnimator implements Parcelable {

    public FadeAnimator(){
        enter = android.R.anim.fade_in;
        exit = android.R.anim.fade_out;
        popEnter = android.R.anim.fade_in;
        popExit = android.R.anim.fade_out;
    }

    protected FadeAnimator(Parcel in){
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

    public static final Creator<FadeAnimator> CREATOR = new Creator<FadeAnimator>() {
        @Override
        public FadeAnimator createFromParcel(Parcel in) {
            return new FadeAnimator(in);
        }

        @Override
        public FadeAnimator[] newArray(int size) {
            return new FadeAnimator[size];
        }
    };

}
