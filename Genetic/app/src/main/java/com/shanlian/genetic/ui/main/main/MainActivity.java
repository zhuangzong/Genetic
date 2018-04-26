package com.shanlian.genetic.ui.main.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.shanlian.genetic.R;
import com.shanlian.genetic.base.AppConst;
import com.shanlian.genetic.base.BaseActivity;
import com.shanlian.genetic.base.MyApplication;
import com.shanlian.genetic.bean.request.MainRequest;
import com.shanlian.genetic.adapter.MyPageAdapter;
import com.shanlian.genetic.ui.login.LoginActivity;
import com.shanlian.genetic.ui.main.circle.CircleFragment;
import com.shanlian.genetic.ui.main.first.FirstFragment;
import com.shanlian.genetic.ui.select.SelectActivity;
import com.shanlian.genetic.util.CUpdateInfo;
import com.shanlian.genetic.util.SPUtils;
import com.shanlian.genetic.util.UIUtils;
import com.shanlian.genetic.util.VersionManager;
import com.shanlian.genetic.view.DividerItemDecoration;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kang on 2018/3/29.
 */

public class MainActivity extends BaseActivity<MainContract.View,MainPresenter> implements MainContract.View {
    @Bind(R.id.tab_layout_main)
    TabLayout mTablayout;
    @Bind(R.id.viewpager_main)
    ViewPager mViewPager;
    @Bind(R.id.iv_main_left)
    ImageView mLogin;
    @Bind(R.id.iv_main_right)
    TextView mSelect;
    @Bind(R.id.rel_main)
    AutoRelativeLayout relMain;

    private TextView mTextView;
    private ImageView mImageView;
    private View view;
    private FirstFragment firstFragment;
    private CircleFragment circleFragment;
    private List<Fragment> fragments = new ArrayList<>();
    private float mPosX, mPosY, mCurPosX, mCurPosY;
    private PopupWindow mPopupWindow;
    private RecyclerView mRecyclerView;

    final static  int UPDATE_CLIENT = 1;
    final static  int GET_UPDATEINFO_ERROR = 2;
    final static  int DOWN_ERROR = 3;
    final static  int NONEW = 4;
    private static CUpdateInfo info;

    //动态申请权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }
        CheckVersion();
    }

    @Override
    public void initView() {
        super.initView();
        StatusBarUtil.setColor(this, UIUtils.getColor(R.color.colorAccent), 10);
        initTabLayout();
        initFragments();
        initViewPager();
        mPresenter.getDataFromNet(new MainRequest());//从接口获取数据
    }

    @Override
    public void initListener() {
        super.initListener();
        SPUtils.getInstance(this).putBoolean(AppConst.ISLOGIN, false);
        mLogin.setOnClickListener(v -> {
            if (SPUtils.getInstance(MainActivity.this).getBoolean(AppConst.ISLOGIN, false)) {
                Log.i(TAG, "islogin");
            } else {
                jumpToActivity(LoginActivity.class);
            }
        });
        mSelect.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SelectActivity.class)));
    }

    /**
     * 下拉弹出框
     */
    private void showPopupWindow() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_main, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        mPopupWindow= new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        mPopupWindow.setOutsideTouchable(true);
        // 设置popWindow的显示和消失动画
        mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 设置PopupWindow是否能响应点击事件
        mPopupWindow.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        mPopupWindow.showAsDropDown(relMain);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);

        mRecyclerView=contentView.findViewById(R.id.rv_popup_main);
        initRecyclerView();

        contentView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    mPosY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();

                    break;
                case MotionEvent.ACTION_UP:
                    if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 25)) {
                        //向下滑動

                    } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 25)) {
                        //向上滑动
                        mPopupWindow.dismiss();
                    }

                    break;
            }
            return true;
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(0,10,0,0));
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        firstFragment = new FirstFragment();
        circleFragment = new CircleFragment();
        fragments.add(firstFragment);
        fragments.add(circleFragment);

    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager(), fragments));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
                mViewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 0) {
//                    mTextView.setTextColor(UIUtils.getColor(R.color.black_3));
//                    mImageView.setImageResource(R.mipmap.arrow_down_black);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    mTextView.setTextColor(UIUtils.getColor(R.color.black_9));
//                    mImageView.setImageResource(R.mipmap.arrow_down_gray);
//                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition()==0){
//                    showPopupWindow();
//                }
            }
        });
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
//        TabLayout.Tab tab = mTablayout.newTab();
//        view=LayoutInflater.from(this).inflate(R.layout.item_tab,null);
//        tab.setCustomView(view);//给每一个tab设置view
//        mTablayout.addTab(tab);
//        mTextView = tab.getCustomView().findViewById(R.id.tv_item_tab);
//        mImageView = tab.getCustomView().findViewById(R.id.iv_item_tab);
//        mTextView.setText(UIUtils.getString(R.string.maintitle1));
//        mTextView.setTextColor(UIUtils.getColor(R.color.black_3));
        mTablayout.addTab(mTablayout.newTab().setText(UIUtils.getString(R.string.category)));
        mTablayout.addTab(mTablayout.newTab().setText(UIUtils.getString(R.string.circle)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onLoadSuccess(String result) {

    }

    @Override
    public void onLoadFail(String msg) {

    }

    /**
     * 检查版本
     */
    public  void CheckVersion() {
        new AsyncTask<String, Void, Object>() {
            //在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用，
            // 后台的计算结果将通过该方法传递到UI 线程，并且在界面上展示给用户.
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                //获取程序当前版本号
                try {
                    PackageManager packageManager = getPackageManager();
                    //getPackageName()是你当前类的包名，0代表是获取版本信息
                    PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
                    String versionName = packInfo.versionName;

                    if (info.GetVersion().equals(versionName)) {
                        //"版本号相同无需升级"
                        Log.i("SL", "版本相同");
                        Message msg = new Message();
                        msg.what = NONEW;
                        handler.sendMessage(msg);
                    } else if (info.GetVersion().equals("GETERROR")) {
                        //网络连接失败，无法更新"
                        Message msg = new Message();
                        msg.what = GET_UPDATEINFO_ERROR;
                        handler.sendMessage(msg);
                    } else {
                        //版本号不同 ,提示用户升级 " 加提示
                        Message msg = new Message();
                        msg.what = UPDATE_CLIENT;
                        handler.sendMessage(msg);
                    }
                } catch (Exception exp) {
                    //更新过程出错，无法更新"
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                }
            }

            //该方法运行在后台线程中，因此不能在该线程中更新UI，UI线程为主线程
            protected Object doInBackground(String... params) {
                try {
                    //从资源文件获取服务器 更新版本号
//                    if (Url.getBaseUrl().equals("http://192.168.62.129:8092/")){
//                        info = VersionManager.getUpdateInfo("http://192.168.62.129:8092/download/UpdateCheckE.xml");
//                    }else if (Url.getBaseUrl().equals("http://pda.yzbx365.cn/")){
//                        info = VersionManager.getUpdateInfo("http://pda.yzbx365.cn/download/UpdateCheckE.xml");
//                    }else {
//                        info = VersionManager.getUpdateInfo("http://61.50.105.94:9002/download/UpdateCheckE.xml");
//                    }
                } catch (Exception e) {
                    // 待处理
                    info.SetVersion("GETERROR");
                }
                return info;
            }

        }.execute();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {
                case NONEW:
                    //不用升级

                    break;
                case UPDATE_CLIENT:
                    //升级程序
                    showUpdateDialog();
                    break;
                case GET_UPDATEINFO_ERROR:
                    //服务器超时
                    Toast.makeText(MyApplication.getContext(), "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();
                    // LoginMain();
                    break;
                case DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(MyApplication.getContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
                    //  LoginMain();
                    break;
            }
        }
    };

    protected  void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本升级");
        builder.setMessage(info.GetDescription());
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //下载apk,更新"
                downLoadApk();
            }
        });
        //当点取消按钮时进行登录
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //  LoginMain();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected  void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = VersionManager.getFileFromServer(info.GetUrl(), pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected  void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(this, "com.shanlian.genetic.fileprovider", file);//在AndroidManifest中的android:authorities值
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");

        } else {
            //执行动作
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setType("application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //执行的数据类型
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}
