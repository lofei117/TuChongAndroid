package info.lofei.app.tuchong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;
import info.lofei.app.tuchong.fragment.CategoryFragment;

/**
 * Created by jerrysher on 15/12/8.
 */
public class CategoryActivity extends BaseActivity{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static final String EXTRA_CATEGORY_NAME_STRING = "extra_category_name_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);

        String categoryString = getIntent().getStringExtra(EXTRA_CATEGORY_NAME_STRING);

        mToolbar.setTitle(categoryString);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CategoryFragment mCategoryFragment = CategoryFragment.newInstance(categoryString);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mCategoryFragment).commitAllowingStateLoss();
    }


    public static void launch(Context context, final String categoryString){
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY_NAME_STRING, categoryString);
        context.startActivity(intent);

    }
}
