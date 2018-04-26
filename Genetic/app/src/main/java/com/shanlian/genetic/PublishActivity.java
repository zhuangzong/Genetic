package com.shanlian.genetic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hai.mediapicker.util.GalleryFinal;
import com.sendtion.xrichtext.RichTextEditor;
import com.sendtion.xrichtext.SDCardUtil;
import com.shanlian.genetic.common.TakePhotoActivity;
import com.shanlian.genetic.util.ImageUtils;
import com.shanlian.genetic.util.ScreenUtils;
import com.shanlian.genetic.util.TimeUtils;
import com.shanlian.genetic.util.VideoThumbLoader;
import com.shanlian.genetic.view.ActionSheet;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.shanlian.genetic.util.UIUtils.showToast;

public class PublishActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener {


    @Bind(R.id.et_new_title)
    EditText etNewTitle;
    @Bind(R.id.tv_new_time)
    TextView tvNewTime;
    @Bind(R.id.tv_new_group)
    TextView tvNewGroup;
    @Bind(R.id.et_new_content)
    RichTextEditor et_new_content;
    @Bind(R.id.content_new)
    LinearLayout contentNew;

    public static final int REQUEST_IMAGE_PICKER = 1000;
    public final static int REQUEST_TAKE_PHOTO = 1001;
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.toolbar)
    AutoLinearLayout toolbar;

    private ProgressDialog insertDialog;
    private Subscription subsInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        initView();
        tvBack.setOnClickListener(v -> finish());
        tvTitle.setText("发布文章");
    }

    private void initView() {
        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("添加");
        tvRight.setOnClickListener(v -> ActionSheet.createBuilder(PublishActivity.this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("相机", "相册")
                .setCancelableOnTouchOutside(true)
                .setListener(PublishActivity.this).show());
        tvNewTime.setText(TimeUtils.getMsgFormatTime(System.currentTimeMillis()));
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
        actionSheet.dismiss();
    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        switch (index) {
            case 0:
                Intent intent = new Intent(PublishActivity.this, TakePhotoActivity.class);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                actionSheet.dismiss();
                break;
            case 1:
                GalleryFinal.selectMedias(this, GalleryFinal.TYPE_ALL, 10, photoArrayList -> {
                    ArrayList<String> list = new ArrayList<>();
                    if (photoArrayList.size() > 0) {
                        for (int i = 0; i < photoArrayList.size(); i++) {
                            list.add(photoArrayList.get(i).getPath());
                        }
                        insertImagesSync(list);
                    }

                });
                break;
        }
    }

    /**
     * 异步方式插入图片
     *
     * @param
     */
    private void insertImagesSync(final ArrayList<String> photos) {
        insertDialog.show();

        subsInsert = Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            try {
                et_new_content.measure(0, 0);
                int width = ScreenUtils.getScreenWidth(PublishActivity.this);
                int height = ScreenUtils.getScreenHeight(PublishActivity.this);
//                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                    //可以同时插入多张图片
                for (String imagePath : photos) {
                    if (imagePath.contains("jpg") || imagePath.contains("png")) {
                        //Log.i("NewActivity", "###path=" + imagePath);
                        Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath, width, height);//压缩图片
                        //bitmap = BitmapFactory.decodeFile(imagePath);
                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        //Log.i("NewActivity", "###imagePath="+imagePath);
                    }

                    subscriber.onNext(imagePath);
                }
                subscriber.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        insertDialog.dismiss();
                        et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), " ");
                        showToast("图片/视频插入成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertDialog.dismiss();
                        showToast("图片/视频插入失败");
                    }

                    @Override
                    public void onNext(String imagePath) {
                        Log.i("qwe", imagePath);
                        if (imagePath.contains("jpg") || imagePath.contains("png")) {
                            et_new_content.insertImage(imagePath, et_new_content.getMeasuredWidth());
                        } else {
                            int width = ScreenUtils.getScreenWidth(PublishActivity.this);
                            int height = ScreenUtils.getScreenHeight(PublishActivity.this);
                            et_new_content.insertVideo(imagePath, VideoThumbLoader.getInstance().
                                    showThumb(imagePath, width, height), PublishActivity.this);
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        et_new_content.releaseVideo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra("path");
                    ArrayList<String> list = new ArrayList<>();
                    Log.i("qwe", path);
                    list.add(path);
                    if (data.getBooleanExtra("take_photo", true)) {
                        //照片
//                        mPresenter.sendImgMsg(ImageUtils.genThumbImgFile(path), new File(path));
                        insertImagesSync(list);
                    } else {

                        //小视频
//                        mPresenter.sendFileMsg(new File(path));

                        insertImagesSync(list);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
