package com.hongyan.life.activity.bill;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseActivity;
import com.hongyan.life.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends BaseActivity {

    private ImageView btnBack;
    private GridView gridViewIncome;
    private GridView gridViewExpend;
    private CategoryAdapter inComeAdapter;
    private CategoryAdapter expendAdapter;

    private int categoryID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        btnBack = findViewById(R.id.image_back);
        gridViewIncome = findViewById(R.id.grid_income);
        gridViewExpend = findViewById(R.id.grid_expend);
        gridViewIncome.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridViewExpend.setSelector(new ColorDrawable(Color.TRANSPARENT));
        inComeAdapter = new CategoryAdapter();
        expendAdapter = new CategoryAdapter();
        gridViewIncome.setAdapter(inComeAdapter);
        gridViewExpend.setAdapter(expendAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ArrayList<Category> incomeCategoryList = Category.getIncomeCategoryList();
        final ArrayList<Category> expandCategoryList = Category.getExpandCategoryList();
        inComeAdapter.setData(incomeCategoryList);
        expendAdapter.setData(expandCategoryList);

        gridViewIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryID = incomeCategoryList.get(position).type;
                showEditDialog();
            }
        });
        gridViewExpend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryID = expandCategoryList.get(position).type;
                showEditDialog();
            }
        });
    }

    private void showEditDialog() {
        //设置样式
        final MyMiddleDialog myMiddleDialog = new MyMiddleDialog(this, R.style.MyMiddleDialogStyle);
        Window window = myMiddleDialog.getWindow();
        //设置边框距离
        window.getDecorView().setPadding(0, 0, 0, 0);
        //设置dialog位置
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置宽高
        lp.width = (int) (DisplayUtils.getScreenW(this) * 0.7);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        //设置点击Dialog外部任意区域关闭Dialog
        myMiddleDialog.setCanceledOnTouchOutside(true);
        myMiddleDialog.show();

        myMiddleDialog.setListener(new MyMiddleDialog.OnInputListener() {
            @Override
            public void callBack(String amount) {
                myMiddleDialog.dismiss();
                commit(amount);
            }
        });
    }


    private void commit(String amount) {
        String remark = "猫猫猫";

        Intent intent = new Intent();
        intent.putExtra("amount", amount);
        intent.putExtra("category", categoryID);
        intent.putExtra("remark", remark);
        intent.putExtra("type", Category.getRecordType(categoryID));
        setResult(300, intent);
        finish();
    }

    class CategoryAdapter extends BaseAdapter {

        private ArrayList<Category> categories = new ArrayList<>();

        public void setData(List<Category> list) {
            categories.clear();
            if (list != null && list.size() > 0) {
                categories.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Category getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            TextView textView = convertView.findViewById(R.id.textView);
            ImageView imageView = convertView.findViewById(R.id.icon);
            final Category item = categories.get(position);
            textView.setText(item.desc);
            imageView.setImageResource(item.icon);
            return convertView;
        }
    }

}