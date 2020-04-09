package com.hongyan.life.activity;

import android.os.Bundle;

import com.hongyan.life.R;
import com.hongyan.life.tab.SubPage;
import com.hongyan.life.tab.TabContainer;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends BaseActivity {

    private List<SubPage> mPageList = new ArrayList<>();
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private ContentPagerAdapter contentAdapter;
    private ViewPager mViewPager;
    private TabContainer tabContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.vp_content);
        tabContainer = findViewById(R.id.tabContainer);
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(contentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabContainer.selectPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ArrayList<SubPage> list = new ArrayList<>();
        SubPage discoverPage = new SubPage();
        discoverPage.fragment = new MeFragment();
        discoverPage.text = "发现";
        discoverPage.drawable = new int[]{R.drawable.icon_discover_s, R.drawable.icon_discover_n};

        SubPage servicePage = new SubPage();
        servicePage.fragment = new MeFragment();
        servicePage.text = "服务";
        servicePage.drawable = new int[]{R.drawable.icon_service_s, R.drawable.icon_service_n};

        SubPage mePage = new SubPage();
        mePage.fragment = new MeFragment();
        mePage.text = "我的";
        mePage.drawable = new int[]{R.drawable.icon_me_s, R.drawable.icon_me_n};

        list.add(discoverPage);
        list.add(servicePage);
        list.add(mePage);
        addSubPage(list);
    }

    public void addSubPage(ArrayList<SubPage> list) {
        if (list == null) {
            return;
        }
        mPageList.addAll(list);
        for (int i = 0; i < mPageList.size(); i++) {
            SubPage subPage = mPageList.get(i);
            mFragmentList.add(subPage.fragment);
            TabContainer.Tab tab = tabContainer.newTab();
            tab.drawable = subPage.drawable;
            tab.text = subPage.text;
            tabContainer.addTab(tab);
        }
        tabContainer.setOnSelectChangeListener(new TabContainer.OnSelectChangeListener() {
            @Override
            public void onChange(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
        contentAdapter.notifyDataSetChanged();
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}