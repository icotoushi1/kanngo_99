package click.nobiru.kanngo_99;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class disp extends Activity{
	
	private final static int WC=TableLayout.LayoutParams.WRAP_CONTENT;
    private final static int FP=TableLayout.LayoutParams.FILL_PARENT;
    private final static int LWC=LinearLayout.LayoutParams.WRAP_CONTENT;
    private final static int LFP=LinearLayout.LayoutParams.FILL_PARENT;
    
    
    long startTime   = 0;
    long endTime     = 0;
    long erapsedTime = 0;
    int  timeSecond  = 0;
    
    int dispWidth =0;
    int dispHeight=0 ;
    int editTextWidth=0;
    int editTextHeight=0;
    int buttonWidth=0;
	int buttonHeight=0;//�f�B�X�v���C�̉�������Ƀ{�^���̍������擾����
	int padding=0;
    
    int textSize=0;
    float dispDensity=0;
    int maxStr=25;
    
    
	disp(WindowManager windowmanager,Bundle savedInstanceState){
		setDispData(windowmanager);
		
        //setContentView(R.layout.main);
	}
	
	
	
	public String strReform(String str){
	
		String tempStr="";
		for(int i=0 ;i<str.length();i++){
			if(i>0 && i%maxStr==0){
				tempStr =tempStr + "\n";
			}
			tempStr =tempStr + str.charAt(i);
			
		}
		return tempStr;
	}
	public void setDispData(WindowManager windowmanager){
    	
        Display disp = windowmanager.getDefaultDisplay();
        
        
        dispWidth = disp.getWidth();
        dispHeight = disp.getHeight();
        
        if(dispWidth > dispHeight){
        	int temp;
        	temp=dispWidth;
        	dispWidth=dispHeight;
        	dispHeight=temp;	
        }
        
        
        textSize=dispWidth/maxStr;
        
    	DisplayMetrics metrics = new DisplayMetrics();  
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);  
        dispDensity = metrics.density;
    	
    }
	
	public void resizeQuestion(){
    	
		setContentView(R.layout.question);
    	TableLayout tableLayout = (TableLayout)findViewById(R.id.tableKekkaHyou);
    	
    	
    /*	Button buttonBeforQuestion = (Button)findViewById(R.id.buttonBeforQuestion);
    	Button buttonEnd = (Button)findViewById(R.id.buttonEnd);
    	Button buttonKaitou = (Button)findViewById(R.id.buttonKaitou);
    	Button buttonNextQuestion = (Button)findViewById(R.id.buttonNextQuestion);
    	Button buttonReturnMeun = (Button)findViewById(R.id.buttonReturnMeun);
    	CheckBox check1 = (CheckBox)findViewById(R.id.check1);
    	CheckBox check2 = (CheckBox)findViewById(R.id.check2);
    	CheckBox check3 = (CheckBox)findViewById(R.id.check3);
    	CheckBox check4 = (CheckBox)findViewById(R.id.check4);
    	CheckBox check5 = (CheckBox)findViewById(R.id.check5);
    	
    	EditText eTQuestionNum = (EditText)findViewById(R.id.eTextQuestionNum);
    	TextView textAnsMsg = (TextView)findViewById(R.id.textAnsMsg);
    	TextView textBigQuestion = (TextView)findViewById(R.id.textBigQuestion);
    	TextView textErorrMsg = (TextView)findViewById(R.id.textErorrMsg);
    	TextView textQuestion = (TextView)findViewById(R.id.textQuestion);
    	TextView textQuestionNum = (TextView)findViewById(R.id.textQuestionNum);
    	TextView textSeikaiRitu = (TextView)findViewById(R.id.textSeikaiRitu);
    	TextView textSetQuestionNum = (TextView)findViewById(R.id.textSetQuestionNum);
    	TextView textTitle = (TextView)findViewById(R.id.textTitle);
    	TextView textKeikaJikan = (TextView)findViewById(R.id. textKeikaJikan);
    	
    	//ImageView image_view1 = (ImageView)findViewById(R.id.image_view1);
    	tableLayout.setMinimumWidth(dispWidth);
    	
    	int height=(int)(textSize*1.5);
    	resizeButton(buttonBeforQuestion,height,textSize);
    	resizeButton(buttonKaitou       ,height,textSize);
    	resizeButton(buttonEnd          ,height,textSize);
    	resizeButton(buttonNextQuestion ,height,textSize);
    	resizeButton(buttonReturnMeun   ,height,textSize);
    	
    	resizeCheckBox(check1,height,textSize);
    	resizeCheckBox(check2,height,textSize);
    	resizeCheckBox(check3,height,textSize);
    	resizeCheckBox(check4,height,textSize);
    	resizeCheckBox(check5,height,textSize);
    	
    	resizeEText(eTQuestionNum,height,textSize);
    	
    	resizeTextView(textAnsMsg,height,textSize);
    	resizeTextView(textBigQuestion,height,textSize);
    	resizeTextView(textErorrMsg,height,textSize);
    	resizeTextView(textQuestion,height,textSize);
    	resizeTextView(textQuestionNum,height,textSize);
    	resizeTextView(textSeikaiRitu,height,textSize);
    	resizeTextView(textSetQuestionNum,height,textSize);
    	resizeTextView(textTitle,height,textSize);
    	resizeTextView(textKeikaJikan,height,textSize);
    */
    }
	
	
	public void resizeEText(EditText editText, int height, int textSize){
		editText.setHeight(height);
		editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }
	public void resizeButton(Button button, int height, int textSize){
    	button.setHeight(height);
    	button.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }
	public void resizeCheckBox(CheckBox checkBox, int height, int textSize){
		checkBox.setHeight(height);
		checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }
    
	public void resizeTextView(TextView textView, int height, int textSize){
    
    	textView.setHeight(height);
    	textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }
	
	public void resizeMain(){
    
		Button buttonkako101_1 = (Button)findViewById(R.id.buttonkako101_1);
		Button buttonkako101_2 = (Button)findViewById(R.id.buttonkako101_2);
		
		int height=(int)(textSize*1.5);
		resizeButton(buttonkako101_1,height,textSize);
		resizeButton(buttonkako101_2,height,textSize);
    	
    }          
}
