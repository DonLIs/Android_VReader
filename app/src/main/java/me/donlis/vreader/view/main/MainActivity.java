package me.donlis.vreader.view.main;

import android.os.Bundle;
import android.widget.Toast;

import me.donlis.vreader.R;
import me.donlis.vreader.anim.HorizontalAnimator;
import me.donlis.vreader.base.AbstractToolbarLessActivity;
import me.donlis.vreader.databinding.ActivityMainBinding;
import me.donlis.vreader.viewmodel.BaseViewModel;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends AbstractToolbarLessActivity<BaseViewModel,ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showContentView();

        initView();
    }

//    @Override
//    protected void setToolBar(Toolbar toolBar) {
//        setSupportActionBar(toolBar);
//    }

    private void initView(){
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.frame, MainFragment.getInstance());
        }
    }

    /**
     * 设置子View的动画为水平过渡
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new HorizontalAnimator();
    }

    private long firstPressedTime;
    /**
     * 按返回键时响应事件
     */
    @Override
    public void onBackPressedSupport() {
        //堆栈里多于1个页面时，退出栈顶页面，否则退出应用
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            //1500ms内双击退出应用
            if (System.currentTimeMillis() - firstPressedTime < 1500) {
                finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstPressedTime = System.currentTimeMillis();
            }
        }
    }

}
