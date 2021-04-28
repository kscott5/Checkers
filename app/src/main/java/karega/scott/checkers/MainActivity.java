package karega.scott.checkers;

import karega.scott.checkers.R;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.findViewById(R.id.startPlayNPass)
        .setOnClickListener(new MainActivity.InternalClickListener(this));

        this.findViewById(R.id.startPhonePlay)
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

    public void startBoardActivity(boolean vsDevice) {   	
    	Intent boardActivityIntent = new Intent(this, BoardActivity.class);
    	boardActivityIntent.putExtra(CheckersEngine.VS_DEVICE, vsDevice);
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
    			case R.id.startPlayNPass:
    				startBoardActivity(/*vs device */ false);
    				break;
    			case R.id.startPhonePlay:
    				startBoardActivity(/*vs device */ true);
    				break;
    			case R.id.exitGame:
    				activity.finish();
    				break;
    		} //end switch    		
    	} //end onClick
    } // end InternalClickListener

} // end MainActivity

