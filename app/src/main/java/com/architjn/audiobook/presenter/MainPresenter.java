package com.architjn.audiobook.presenter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.architjn.audiobook.R;
import com.architjn.audiobook.adapter.ViewPagerAdapter;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.BookChapter;
import com.architjn.audiobook.interactor.MainInteractor;
import com.architjn.audiobook.ui.IMainView;
import com.architjn.audiobook.ui.activity.FolderChooserViewActivity;
import com.architjn.audiobook.ui.activity.MainActivity;
import com.architjn.audiobook.ui.fragment.AllAudioBookViewFragment;
import com.architjn.audiobook.ui.fragment.FinishedAudioBookFragment;
import com.architjn.audiobook.ui.fragment.NewAudioBookFragment;
import com.architjn.audiobook.ui.fragment.OnGoingAudioBookFragment;
import com.architjn.audiobook.utils.BookUtils;
import com.architjn.audiobook.utils.PermissionChecker;
import com.architjn.audiobook.utils.PrefUtils;
import com.architjn.audiobook.utils.Utils;

import java.util.ArrayList;

/**
 * Created by HP on 22-07-2017.
 */

public class MainPresenter implements IMainPresenter {
    private final Context context;
    private final PrefUtils pref;
    private MainInteractor interactor;
    private IMainView view;
    private ViewPager viewPager;

    public MainPresenter(Context context) {
        this.view = (IMainView) context;
        this.context = context;
        this.interactor = new MainInteractor(context, this);
        pref = PrefUtils.getInstance(context);
        init();
    }

    private void init() {
        if (pref.getAudioBookFolderPath() == null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(R.string.select_directory)
                    .setMessage(R.string.select_directory_dialog_msg)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (view != null)
                                if (PermissionChecker.requestForPermission(context,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        R.string.read_permission_request, MainActivity.READ_PERMISSION_REQUEST))
                                    view.startFolderChooser();
                        }
                    })
                    .create().show();
        } else interactor.scanForNewAudioBooks();
    }

    public void setupViewPager(ViewPager viewPager, FragmentManager supportFragmentManager) {
        this.viewPager = viewPager;
        ViewPagerAdapter adapter = new ViewPagerAdapter(context, supportFragmentManager);
        adapter.addFragment(new AllAudioBookViewFragment(), R.string.all);
        adapter.addFragment(new NewAudioBookFragment(), R.string.new_txt);
        adapter.addFragment(new OnGoingAudioBookFragment(), R.string.ongoing);
        adapter.addFragment(new FinishedAudioBookFragment(), R.string.finished);
        viewPager.setAdapter(adapter);
    }

    public void setTabLayout(TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void startFolderChooser() {
        if (view != null)
            view.startFolderChooser();
    }
}
