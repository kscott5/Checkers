package karega.scott.checkers;

import karega.scott.checkers.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.findViewById(R.id.startGame)
        .setOnClickListener(new MainActivity.InternalClickListener(this));
        
        this.findViewById(R.id.exitGame)
        .setOnClickListener(new MainActivity.InternalClickListener(this));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }    

    public void startBoardActivity() {
    	Intent boardActivityIntent = new Intent(this, BoardActivity.class);
    	this.startActivity(boardActivityIntent);
    }

    /**
     * 
     * @author Administrator
     *
     */
    public class InternalClickListener implements OnClickListener {
    	private MainActivity activity;
    	
    	public InternalClickListener(MainActivity activity) {
    		this.activity = activity;
    	}
    	
    	@Override
    	public void onClick(View v) {
    		switch(v.getId()) {
    			case R.id.startGame:
    				startBoardActivity();
    				break;
    			case R.id.exitGame:
    				activity.finish();
    				break;
    		} //end switch    		
    	} //end onClick
    } // end InternalClickListener

} // end MainActivity

