package karega.scott.checkers.ui;

import karega.scott.checkers.Logger;
import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.DeviceTask;
import karega.scott.checkers.R;

import java.util.Timer;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.graphics.Rect;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.AttributeSet;
import android.content.Context;

import android.view.WindowInsets;
import android.widget.RelativeLayout;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 * The activity used to display the checker board.
 */
public class BoardActivity extends Activity implements Callback {
	private static final String LOG_TAG = "BoardActivity";
	
	// @Inject and singleton are the same.
	public static CheckersEngine gameEngine = new CheckersEngine(new Logger() {
		public void it(int type, String tag, String message) {
			Log.d(tag,message);
		}
	});

	private Handler handler;
	private Timer deviceTimer;

	@Override
	protected void finalize() {
		if(this.deviceTimer != null) {
			deviceTimer.cancel();
			deviceTimer = null;
		}
	}
	
	@Override
	public boolean handleMessage(Message message) {
		if(message.what != CheckersEngine.INVALIDATE_VIEW_MESSAGE_HANDLER)
			return false;

		if(!(message.obj /*not*/ instanceof View))
			return false;

		((View)message.obj).invalidate();
		return true;
	}

	protected void handleMessage(int what, Object object) {
		if(object == null) return;

		Message message = Message.obtain(handler, what, object);
		message.sendToTarget();
	}

	public void handleViewChanges(CheckerBoardSquare view) {
		// When the looper is not available, changes are
		// applied on a secondary thread. This thread updates
		// device play on the UI.
		if(Looper.getMainLooper() == null)
			handleMessage(CheckersEngine.INVALIDATE_VIEW_MESSAGE_HANDLER, view);
		else
			view.invalidate();
	}

	// TODO: Do I really need these instance variables
	private Button exitGame;
	private Button newGame;
	private CheckerBoardView boardView;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "On create called");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		this.setTitle(R.string.app_name);
		
		Intent intent = this.getIntent();
		boolean vsDevice = intent.getBooleanExtra(CheckersEngine.VS_DEVICE, true);
	
		gameEngine.newGame();
		
		if(vsDevice) {
			handler = new Handler(Looper.getMainLooper(), this);

			this.deviceTimer = new Timer();
			this.deviceTimer.schedule(new DeviceTask(gameEngine), 100, 1000);
		}

		boardView = (CheckerBoardView) this.findViewById(R.id.boardView);
	
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		Log.d(LOG_TAG, "Checker board view display mertics width: " + width + ", height: " + height);

		try {
		RelativeLayout boardLayout = this.findViewById(R.id.boardLayout);
		WindowInsets windowInsets = boardLayout.getRootWindowInsets();

		Log.d(LOG_TAG, "Checker board view window stable inset left: " + windowInsets.getStableInsetLeft()  + ", top: " + windowInsets.getStableInsetTop());
		Log.d(LOG_TAG, "Checker board view system window inset left: " + windowInsets.getSystemWindowInsetLeft()  + ", top: " + windowInsets.getSystemWindowInsetTop());
} catch(Exception e) {
	Log.e(LOG_TAG, e.getMessage());
}
		width = boardView.getWidth();
		exitGame = (Button) this.findViewById(R.id.exitGame);
		exitGame.setWidth(width/2);
		exitGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
		
		newGame = (Button) this.findViewById(R.id.newGame);
		newGame.setWidth(width/2);
		newGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
	}	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(LOG_TAG, "On create options menu called");
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_board, menu);
		return true;
	}
	
	/**
	 * Internal listener used when this activity child view is clicked
	 * @author Administrator
	 *
	 */
	public class InternalClickListener implements OnClickListener {
		private static final String LOG_TAG = "InternalClickListener";
		
		private BoardActivity activity;
		
		public InternalClickListener(BoardActivity activity) {
			this.activity = activity;
		}
		
		@Override
		public void onClick(View view) {
			Log.d(LOG_TAG, "On click");
			switch(view.getId())
			{
				case R.id.newGame:					
					gameEngine.newGame();
					break;
					
				case R.id.exitGame:
					gameEngine.exitGame();
					activity.finish();
					break;
			} //end switch
		} // end onClick		
	} // end InternalClickListener
} // end BoardActivity
