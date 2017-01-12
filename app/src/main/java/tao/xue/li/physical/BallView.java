package tao.xue.li.physical;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class BallView extends SurfaceView implements SurfaceHolder.Callback{

	public static final int V_MAX = 55;	//С???????????
	public static final int V_MIN = 30;	//С??????????С?
	public static final int WOODEDGE = 140;	//???????
	public static int GROUND_LING;	//?????????????????·???Y???????С????????
	//???????????????趨
	public static final int UP_ZERO = 30;	//С???????????У????С????????????????0
	public static final int DOWN_ZERO = 60;	//С????????????????С?????????0
	Bitmap[] bitmapArray = new Bitmap[1];	//????С???????
	Bitmap bmpBack;	//??????
	Bitmap bmpWood;	//?????
	String fps = "FPS??N/A";		//???????????????????????????"FPS:N/A"???????????
	int ballNumber = 1;	//С???????
	ArrayList<Movable> alMovable = new ArrayList<Movable>();	//С???????б?
	DrawThread dt;	//?????????????
	private SurfaceHolder sfh;

	public BallView(Context Activity) {
		super(Activity);

		sfh = this.getHolder();
		sfh.addCallback(this);
		initBitmaps(getResources());	//???д??????????????
		initMovables();	//???д????????????С??
		dt = new DrawThread(this, sfh);	//???????????
		// TODO Auto-generated constructor stub
	}

	//?????С??
	public void initMovables(){
		Random r = new Random();	//??????????????
		for(int i = 0; i < ballNumber; i++){
			int index = r.nextInt(32);	//???????????
			Bitmap tempBitmap = null;
			tempBitmap = bitmapArray[0];	//??????????????????????????????
			int startX = 0;
			int startY = 300;
			int finalX = 200;

			float startVy = -(float)(1000);
			double t1 = -startVy/BallThread.g;
			GROUND_LING = 800;
			double s = (startVy*t1+0.5*BallThread.g*t1*t1);
			double t2 =Math.sqrt(((2*(GROUND_LING-startY+s))/BallThread.g));
			double t = t1+t2;
			float startVx = (float)((finalX-startX)/(t));

			Movable m = new Movable(startX, startY, startVx,startVy,tempBitmap.getWidth()/2, tempBitmap);
			alMovable.add(m);	//?????????????arrayList??
		}
	}
	//???????
	public void initBitmaps(Resources r){
		bitmapArray[0] = BitmapFactory.decodeResource(r, R.drawable.ball_red_small);	//???С??
		bmpBack = BitmapFactory.decodeResource(r, R.drawable.back);
		bmpWood = BitmapFactory.decodeResource(r, R.drawable.wood);
	}

	//??????????????????
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(bmpBack, 0, 0, null);	//???????,?м???????????????????????λ??
		canvas.drawBitmap(bmpWood, 0, 360, null);	//???????
		canvas.drawBitmap(bmpWood, 200, 860, null);	//???????
		for(Movable m : alMovable){		//????????С?????????
			m.drawSelf(canvas);
		}
		Paint p = new Paint();	//????????????
		p.setColor(Color.BLUE);	//??????????
		p.setTextSize(18);	//?????????С
		p.setAntiAlias(true);	//???????????
		canvas.drawText(fps, 30, 30, p);	//????fps????????
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
	                           int height) {
		// TODO Auto-generated method stub

	}

	//surfaceView??????????????????????????????????????д????
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
//		GROUND_LING  = this.getHeight();	//???surface???????????????????
		if(!dt.isAlive()){
			dt.start();	//?????????????
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		dt.flag = false;
		dt = null;
	}

}