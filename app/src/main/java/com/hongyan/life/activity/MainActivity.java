package com.hongyan.life.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hongyan.life.R;
import com.hongyan.life.activity.bill.BillFragment;
import com.hongyan.life.activity.bill.Category;
import com.hongyan.life.activity.calc.CalcFragment;
import com.hongyan.life.activity.home.HomeFragment;
import com.hongyan.life.activity.shares.SharesFragment;
import com.hongyan.life.activity.translate.TranslateFragment;
import com.hongyan.life.tab.SubPage;
import com.hongyan.life.tab.TabContainer;
import com.lljjcoder.style.citylist.utils.CityListLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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
        Category.init();
        CityListLoader.getInstance().loadCityData(this);
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
        SubPage homePage = new SubPage();
        homePage.fragment = new HomeFragment();
        homePage.text = "首页";
        homePage.drawable = new int[]{R.drawable.icon_home_s, R.drawable.icon_home_n};

        SubPage billPage = new SubPage();
        billPage.fragment = new BillFragment();
        billPage.text = "记帐";
        billPage.drawable = new int[]{R.drawable.icon_account_s, R.drawable.icon_account_n};

        SubPage sharesPage = new SubPage();
        sharesPage.fragment = new SharesFragment();
        sharesPage.text = "股票";
        sharesPage.drawable = new int[]{R.drawable.icon_stock_s, R.drawable.icon_stock_n};

        SubPage transPage = new SubPage();
        transPage.fragment = new TranslateFragment();
        transPage.text = "翻译";
        transPage.drawable = new int[]{R.drawable.icon_translation_s, R.drawable.icon_translation_n};

        SubPage calcPage = new SubPage();
        calcPage.fragment = new CalcFragment();
        calcPage.text = "计算器";
        calcPage.drawable = new int[]{R.drawable.icon_calculator_s, R.drawable.icon_calculator_n};

        list.add(homePage);
        list.add(billPage);
        list.add(sharesPage);
        list.add(transPage);
        list.add(calcPage);
        addSubPage(list);
    }

    @Override
    public int setStatusBarColor() {
        return 0xffffff;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        int currentItem = mViewPager.getCurrentItem();
        BaseFragment baseFragment = mFragmentList.get(currentItem);
        baseFragment.onActivityResult(requestCode,resultCode,data);
    }
}