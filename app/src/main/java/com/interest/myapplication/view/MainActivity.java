package com.interest.myapplication.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.interest.myapplication.R;
import com.interest.myapplication.Utils.CacheUtil;
import com.interest.myapplication.Utils.Const;
import com.interest.myapplication.Utils.PatternUtil;
import com.interest.myapplication.view.book.BooksFragment;
import com.interest.myapplication.view.chat.ChatFragment;
import com.interest.myapplication.view.news.MainFragment;
import com.interest.myapplication.view.save.SaveBackHanderInterface;
import com.interest.myapplication.view.save.SaveBackHandleFragment;
import com.interest.myapplication.view.save.SaveBookFragment;
import com.interest.myapplication.view.save.SaveFragment;
import com.interest.myapplication.view.save.SaveNewsFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import c.b.BP;
import c.b.PListener;

public class MainActivity extends BaseActivity implements SaveBackHanderInterface{
    @ViewInject(R.id.tlCustom)
    private Toolbar toolbar;
    @ViewInject(R.id.dlCustom)
    private DrawerLayout drawerLayout;
    @ViewInject(R.id.navigation)
    private NavigationView navigationView;
    private ChatFragment chatFragment;      //注册chatFragment
    private BooksFragment booksFragment;    //注册bookFragment
    private MainFragment newsFragment;      //注册newsFragment
    private SaveFragment saveFragment;      //注册saveFragment
    private SaveBookFragment saveBookFragment;
    private SaveNewsFragment saveNewsFragment;
    private Fragment nowFragment;   //当前的Fragment对象
    private ProgressDialog pgDialog;
    private AlertDialog dialog;
    private AlertDialog dialog1;
    private SaveBackHandleFragment saveBackHandleFragment;
    private long fisrtBackPressed;

    /**
     * 侧滑栏的栏目
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        //防止Fragment重叠，讲上次保存的fragment弹出
        if(savedInstanceState!=null){
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }
        BP.init(this, Const.APPID);
        setToolBar();
        setFragments();
        setListener();
        setDialogView();
    }

    private void setDialogView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(this,R.layout.dialog_about,null);
        dialog = builder.setCancelable(false).setView(view).create();
        View view1 = View.inflate(this,R.layout.dialog_money,null);
        TextView tvPay = (TextView) view1.findViewById(R.id.tvDialogPay);
        TextView tvCancle = (TextView) view1.findViewById(R.id.tvDialogCancle);
        final TextInputLayout tilMoney = (TextInputLayout) view1.findViewById(R.id.tilMoney);
        dialog1 = builder.setCancelable(false).setView(view1).create();
        view.findViewById(R.id.tvAboutSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dialog.dismiss();
            }
        });
        view.findViewById(R.id.tvAboutMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog1.show();
            }
        });
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price =tilMoney.getEditText().getText().toString();
                if (PatternUtil.check(price)) {
                    payByAli(Double.parseDouble(price));
                    dialog1.dismiss();
                }else{
                    tilMoney.getEditText().setText("");
                    tilMoney.setError("请输入正确金额，谢谢");
//                    Toast.makeText(MainActivity.this, "请输入正确金额，谢谢", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    /**
     * 初始化Fragments
     */
    private void setFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (newsFragment == null) {
            newsFragment = new MainFragment();
        }
        nowFragment = newsFragment;
        transaction.replace(R.id.llContainer, newsFragment).commit();
    }

    /**
     * 设置toolbar的属性
     */
    private void setToolBar() {
        toolbar.setTitle("首页");   //设置toolbar标题
        toolbar.setTitleTextColor(Color.WHITE); //设置toolbar标题颜色
        setSupportActionBar(toolbar);   //开启toolbar
        getSupportActionBar().setHomeButtonEnabled(true);   //设置toolbar返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
    }

    /**
     * 设置监听器，包括ToolBar和DrawLayout的联动，和NavigationView的Item的点击监听
     */
    private void setListener() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                try {
                    if (!"关于".equals(item.getTitle())){
                        getSupportActionBar().setTitle(item.getTitle());
                    }
                    switch (item.getItemId()) {
                        case R.id.menu1: //首页
                            if (newsFragment == null) {
                                newsFragment = new MainFragment();
                            }
                            switchContent(nowFragment, newsFragment);
                            break;
                        case R.id.menu2: //看书
                            if (booksFragment == null) {
                                booksFragment = new BooksFragment();
                            }
                            switchContent(nowFragment, booksFragment);
                            break;
                        case R.id.menu3: //调戏机器人
                            if (chatFragment == null) {
                                chatFragment = new ChatFragment();
                            }
                            switchContent(nowFragment, chatFragment);
                            break;
                        case R.id.menu4: //收藏
                            if (saveFragment==null){
                                saveFragment = new SaveFragment();
                                saveFragment.setOnButtonClick(new SaveFragment.OnButtonClick(){
                                    @Override
                                    public void clickButton(int position) {
                                        switch (position){
                                            case 0:
                                                getSupportActionBar().setTitle("图书");
                                                if (saveBookFragment==null){
                                                    saveBookFragment = new SaveBookFragment();
                                                }
                                                switchContent(nowFragment,saveBookFragment);
                                                break;
                                            case 1:
                                                getSupportActionBar().setTitle("话题");
                                                if (saveNewsFragment == null){
                                                    saveNewsFragment = new SaveNewsFragment();
                                                }
                                                switchContent(nowFragment,saveNewsFragment);
                                                break;
                                        }
                                    }
                                });
                            }
                            switchContent(nowFragment,saveFragment);
                            break;
                    }
                    drawerLayout.closeDrawers();    //隐藏DrawLayout的布局
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 切换到指定的Fragment界面
     *
     * @param from 起始Fragment界面
     * @param to   需要切换的Fragment界面
     */
    public void switchContent(Fragment from, Fragment to) {
        if (nowFragment != to) {
            nowFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(from).add(R.id.llContainer, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
    private void payByAli(double price) {
        double p = price<0.02?0.02:price;
        showDialog("正在获取订单...");
        BP.pay(this, "商品名称", "描述",p, true, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(MainActivity.this, "支付结果未知,请稍后手动查询",
                        Toast.LENGTH_SHORT).show();
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(MainActivity.this, "支付成功!", Toast.LENGTH_SHORT)
                        .show();
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                Toast.makeText(MainActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                        .show();
                hideDialog();
            }
        });
    }

    private void hideDialog() {
        if (pgDialog != null && pgDialog.isShowing())
            try {
                pgDialog.dismiss();
            } catch (Exception e) {
            }
    }

    private void showDialog(String message) {
        try {
            if (pgDialog == null) {
                pgDialog = new ProgressDialog(this);
                pgDialog.setCancelable(true);
            }
            pgDialog.setMessage(message);
            pgDialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    @Override
    public void onBackPressed() {
        if (saveBackHandleFragment == null || !saveBackHandleFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (System.currentTimeMillis()-fisrtBackPressed>2000) {
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
                    fisrtBackPressed = System.currentTimeMillis();
                }else {
                    super.onBackPressed();
                }
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void setSelectedFragment(SaveBackHandleFragment saveBackHandleFragment) {
        this.saveBackHandleFragment = saveBackHandleFragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.toString()) {
            case "关于":
                dialog.show();
                break;
            case "清除缓存":
                CacheUtil.cleanCache(MainActivity.this);
                break;
            case "安全退出":
                ProgressDialog.show(MainActivity.this,"安全关闭","正在关闭",true,false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                },500);
                break;
        }
        return true;
    }
}