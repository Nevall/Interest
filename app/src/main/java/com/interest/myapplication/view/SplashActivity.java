package com.interest.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.interest.myapplication.R;
import com.interest.myapplication.model.SplashActivityBiz;

import java.io.File;


public class SplashActivity extends Activity{

	private ImageView ivStart;
	private SplashActivityBiz biz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		//��ʼ���ؼ�
		initViews();
	}

	private void initViews() {
		biz = new SplashActivityBiz(this);
		ivStart = (ImageView)findViewById(R.id.iv_start);
		//�Ӹ�Ŀ¼�л�ȡͼƬ��Դ
		File dir = getFilesDir();
		final File file = new File(dir,"start.jpg");
		if (file.exists()) {
			ivStart.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
		}else {
			ivStart.setImageResource(R.drawable.start);
		}
		//����ͼƬ����
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f , Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setFillAfter(true);//����ִ�����ͣ�������һ֡
		scaleAnimation.setDuration(3000);//����ʱ��
		//Ϊ�������ü�����
		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				//������ʼʱִ��
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// �����ظ�ʱִ��
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// ��������ʱִ��
				//����Http�����ȡ��������������ͼ��
				biz.loadStratImage(file);
				//��MainActivity
				startActivity();
			}
		});
		//��������
		ivStart.startAnimation(scaleAnimation);
	}

	/**
	 * ��MainActivity
	 */
	protected void startActivity() {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		finish();
	}
}
