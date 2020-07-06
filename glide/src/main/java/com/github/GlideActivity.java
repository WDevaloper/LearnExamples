package com.github;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LruCache;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.github.glide.R;
import com.github.glide.load.LoaderTest;

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        LruCache<String, String> lruCache = new LruCache<>(2);

        Glide.with(this)
                .load("")
                .into(new ImageView(this));

        new Thread() {
            @Override
            public void run() {

                new LoaderTest()
                        .test(GlideActivity.this);
            }
        }.start();

    }


    /**
     * @param fm
     * @param parentHint
     * @param isParentVisible
     * @return
     */
//    @NonNull
//    private SupportRequestManagerFragment getSupportRequestManagerFragment(
//            @NonNull final FragmentManager fm, @Nullable Fragment parentHint, boolean isParentVisible) {
//        //fragment被添加到Activity上才可以被找到
//        SupportRequestManagerFragment current =
//                (SupportRequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
//
//        if (current == null) {
//            //从临时缓存中查找,,,为了阻止重复提交fragment
//            current = pendingSupportRequestManagerFragments.get(fm);
//            if (current == null) {
//                current = new SupportRequestManagerFragment();
//                current.setParentFragmentHint(parentHint);
//                if (isParentVisible) {
//                    current.getGlideLifecycle().onStart();
//                }
//                //添加到临时缓存
//                pendingSupportRequestManagerFragments.put(fm, current);
//                //commitAllowingStateLoss也是使用handler（主线程）
//                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
//                // handler（主线程） 根据handler消息机制原理，只有commitAllowingStateLoss这个handler
//                // 消息执行完成，才会被执行ID_REMOVE_SUPPORT_FRAGMENT_MANAGER这个消息
//                // 这是为了保证RootFragment唯一性
//                // Glide.with(this).load(url_1).into(mImageView_1); m2
//                // Glide.with(this).load(url_2).into(mImageView_2); m3
//                // 就是因为m2 调用commitAllowingStateLoss也是用Handler异步
//                // 所以需要pendingSupportRequestManagerFragments保存防止重复提交
//                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
//            }
//        }
//        return current;
//    }
}
