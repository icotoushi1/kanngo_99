package click.nobiru.kanngo_99;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;

public class Kanngo_kokushiActivity extends Activity {

	//test
	public int kai;
	public int kai2;
	public int am_pm; 
	private DataBaseHelper mDbHelper;  
	//private disp disp ;
    private SQLiteDatabase db;  
    private final static String DB_NAME = "kanngo_kokushi.db";
    //private final static String TABLE_NAME ="kanngo";
    //private static final String[] COLUMNS = {"_id", "question","ans"};
    private Cursor cursor;
    int rowMax=120;
    int dispWidth =0;
    int dispHeight=0;
    int textSize=0;
    int maxStr=20;
    
    
    
    boolean kekkaHyouFrg=false;
    //int 
    boolean kekkaAns[] = new boolean[121]; 
    boolean kekkaCnt[] = new boolean[121]; 
    
    long startTime =0;// System.currentTimeMillis();
    
    /*
    int editTextWidth=0;
    int editTextHeight=0;
    int buttonWidth=0;
	int buttonHeight=0;//ディスプレイの横幅を基準にボタンの高さを取得する
	int padding=0;
    */
   // int textSize=0;
    float dispDensity=0;
    //int maxStr=23;
    
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WindowManager windowmanager
				= (WindowManager)getSystemService(WINDOW_SERVICE);
        //disp = new disp(windowmanager,savedInstanceState);
        setDispData();
        resizeMain();
        setDatabase();
        //setAdmobs();
        
    }

	
	public void setAdmobs(){
//	        setAdmob(R.id.linearAdmobTop);
//	        setAdmob(R.id.linearAdmobBottm);
	 } 
     
    public void onClickKako101_1(View view){
    	
    	getData(101,1);
    	startQuestion(R.string.kako101_1);
    	preProces();
    	
    }
    public void onClickKako101_2(View view){
    	getData(101,2);
    	startQuestion(R.string.kako101_2);
    	preProces();
    }
    public void onClickKako100_1(View view){
    	getData(100,1);
    	startQuestion(R.string.kako100_1);
    	preProces();
    }
    public void onClickKako100_2(View view){
    	
    	getData(100,2);
    	startQuestion(R.string.kako100_2);
    	preProces();
    }
    public void onClickKako99_1(View view){
    	getData(99,1);
    	startQuestion(R.string.kako99_1);
    	preProces();
    }
    
    public void onClickKaitou(View view){
    	//
    	CheckBox cb1 = (CheckBox)findViewById(R.id.check1);
    	CheckBox cb2 = (CheckBox)findViewById(R.id.check2);
    	CheckBox cb3 = (CheckBox)findViewById(R.id.check3);
    	CheckBox cb4 = (CheckBox)findViewById(R.id.check4);
    	CheckBox cb5 = (CheckBox)findViewById(R.id.check5);
    	
    	
    	int ans  = cursor.getInt(10);
    	int seigo=0;
	    int ans1 = ans/10; 
	    int ans2 = ans%10;
	    
	 	int ansNum=1;
   		if(ans>10){ansNum=2;}

	    int checkNum=0;
	    int correctNum = 0;
	    
	    String ansMsg="";
	     
	     if(kekkaHyouFrg == false){
	     //回答表作成
	    	kekkaHyouFrg=true;
	     	//createKekkaHyou();
	     }
	     if( cb1.isChecked()){
	     	checkNum = checkNum + 1;
	     	if(ans1 == 1 || ans2 ==1){
	     		correctNum = correctNum+1;
	     	}
	     }
	     if( cb2.isChecked()){
	     	checkNum = checkNum + 1;
	     	if(ans1 == 2 || ans2 ==2){
	     		correctNum = correctNum+1;
	     	}
	     }
	     if( cb3.isChecked()){
	     	checkNum = checkNum + 1;
	     	if(ans1 == 3 || ans2 ==3){
	     		correctNum = correctNum+1;
	     	}
	     }
	     if( cb4.isChecked()){
	     	checkNum = checkNum + 1;
	     	if(ans1 == 4 || ans2 ==4){
	     		correctNum = correctNum+1;
	     	}
	     }
	     
	     if(cb5.getVisibility() != CheckBox.GONE){
		     if( cb5.isChecked()){
		     	
		     	checkNum = checkNum + 1;
		     	if(ans1 == 5 || ans2 ==5){
		     		correctNum = correctNum+1;
		     	}
		     }
	     }
	     
	     if (correctNum >= 1 && correctNum == ansNum && checkNum==ansNum) {
	       ansMsg= "☆　正解です。☆";
	       
	       seigo=1;
	     }
	     else if (checkNum!=ansNum ){
	       ansMsg = "×　"+ ansNum +"つ選択してください。×";
	       seigo=2;
	     }
	     else{
	       ansMsg  = "×　不正解です。×";
	       seigo=3;
	     }
	     inputKekka(seigo,ansMsg); 
	     inputKekkaTextAns(seigo,ansNum,ans1,ans2);
    	 
    }
    public void inputKekkaTextAns(int seigo,int ansNum,int ans1,int ans2){
    	if(seigo!=1){
    		TextView textAns = (TextView)findViewById(R.id.textAns);
    		
    		String str="";


    		str ="正解　"+ans2;
    		if(ansNum==2) str =str+"　と　"+ans1;
    		
    		textAns.setText(str + "　");
    	}
    }
    public void inputKekka(int seigo ,String ansMsg){
    	
    	int ansColor=0;
	    int ansTextColor=0;
	    String maruBatu="";
	    int qNum  = cursor.getInt(1);
	    
	    
    	if(seigo==1){
    		ansTextColor=Color.YELLOW; //"goldenrod";
  	        ansColor=R.color.green;//"khaki"
  	        kekkaAns[qNum]=true;
  	        maruBatu="○";
  	        kekkaCnt[qNum]=true;
  	      
    	}else if(seigo==2){
    		ansTextColor=Color.RED;
 	        ansColor=R.color.pink;
 	        maruBatu="×";
 	       kekkaAns[qNum]=false;
	       kekkaCnt[qNum]=true;
 	       
    	}else{
    		ansTextColor=Color.RED;
    		ansColor=R.color.pink;
    		maruBatu="×";
 	      kekkaAns[qNum]=false;
	      kekkaCnt[qNum]=true;
	    
    	}
    	inputKekkaMsg(ansMsg,ansTextColor);
    	inputBorderColor(ansColor);
    	inputKekkaHyou(maruBatu);
    	
    	
    	
    }
    
    public void inputSeikaiRitu(){
    	
    	double seikaiCnt=0;
		double kaitouCnt=0;
		double ritu=0;
		int dispRitu=0;
		
	     for(int i=1;i<=120;i++ ){
		     if(kekkaAns[i]){seikaiCnt++;}
		     if(kekkaCnt[i]){kaitouCnt++;}
		 }
		 if(seikaiCnt > 0 && kaitouCnt > 0){
		 	ritu = (seikaiCnt/kaitouCnt)*100;
		 	dispRitu = (int)ritu;
		 }
		 TextView textSeikaiRitu = (TextView)findViewById(R.id.textSeikaiRitu);
		 textSeikaiRitu.setVisibility(View.VISIBLE);
		 textSeikaiRitu.setText((int)kaitouCnt+"問中" +
				 (int)seikaiCnt + "問正解：正解率 " + dispRitu +"％");
    }
    public void preProces(){
    	cratKekkaHyou();
    	setStartTime();
    	
    }
    public void cratKekkaHyou(){
    	TableLayout tableKekkaHyou  = (TableLayout)findViewById(R.id.tableKekkaHyou);
    	int WC=TableLayout.LayoutParams.WRAP_CONTENT;
    	int kekkaId=0;
    	
    	for (int j=0;j<24;j++) {
            //行の生成
            TableRow row=new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(WC,WC));
            row.setGravity(Gravity.CENTER);//中央寄せ
            //row.setBackgroundColor (Color.WHITE);
            tableKekkaHyou.addView(row);
            
            //要素の追加
            for (int i=0;i<5;i++) {
            	TextView textKekka =new TextView(this);
            	textKekka.setGravity(Gravity.CENTER);
            	textKekka.setPadding(0, 0, 0, 0);
            	kekkaId++;
            	textKekka.setId(kekkaId);
            	textKekka.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            	textKekka.setVisibility(TextView.GONE);
            	row.addView(textKekka);
            }
        }
     
    }
    public void inputKekkaHyou(String inMaruBatu){
    	
    	int id = cursor.getInt(1);
    	TextView textKekka =(TextView)findViewById(id);
    	textKekka.setVisibility(View.VISIBLE);
    	//textKekka.setBackgroundResource(R.drawable.border);
    	textKekka.setText(id+":"+inMaruBatu);
    		
    	inputSeikaiRitu();
    	inputKeikaJikan();
    }
    public void inputBorderColor(int inAnsColor){
    	LinearLayout linearSelectOutLinet = (LinearLayout)findViewById(R.id.linearSelectOutLine);
    	linearSelectOutLinet.setBackgroundResource(inAnsColor);
    }
    public void inputKekkaMsg( String inStr,int inAnsTextColor){
    	
    	TextView textAnsMsg = (TextView)findViewById(R.id.textAnsMsg);
    	textAnsMsg.setVisibility(View.VISIBLE);
    	textAnsMsg.setText(inStr);
    	textAnsMsg.setTextColor(inAnsTextColor);
    	
    
    	
    	/*
    	 if(ans1==0){
	       	document.getElementById("text_msg").innerHTML="答えは："+ans2+"�@�ł�";
	       }else{
	       	document.getElementById("text_msg").innerHTML="答えは："+ans2+"    "+ans1+"�@�ł�";
	       }
	       kekka_ans[td_num] = false;
	       kekka_cnt[td_num] = true;
	     var seikai_cnt=0;
		 var kaitou_cnt=0;
		 
	     for(var i=1;i<=120;i++ ){
		     if(kekka_ans[i]){seikai_cnt++;}
		     if(kekka_cnt[i]){kaitou_cnt++;}
		 }
		 var ritu=0;
		 
		 if(seikai_cnt > 0 && kaitou_cnt > 0){
		 	ritu = Math.floor((seikai_cnt/kaitou_cnt)*100);
		 } 
	     
	      *
	      */
    }
    
    public void inputKeikaJikan(){
    	long jikan = System.currentTimeMillis() - startTime;
    	int time = (int)(jikan/1000);
    	int hun = time/60;
    	int byou = time % 60;
    	TextView textKeikaJikan = (TextView)findViewById(R.id.textKeikaJikan);
    	textKeikaJikan.setVisibility(View.VISIBLE);
    	textKeikaJikan.setText(hun+"分"+byou+"秒");
    	
    }
  /*  public void onClickETextQuestionNum(View view){
    	EditText eTextQuestionNum = (EditText)findViewById(R.id.eTextQuestionNum);
    	
    	if(!eTextQuestionNum.hasFocusable()){
    		eTextQuestionNum.setFocusable(true);
    	}
    	
    	EditText eTextQuestionNum = (EditText)view;
    	
    	if(!eTextQuestionNum.hasFocusable()){
    		eTextQuestionNum.setFocusable(true);
    	}
    	
    }*/
    
    
    public void onClickTSizeCgangeBig(View view){
    	
    	int textSize= dispWidth/13;
    	tSizeCgange(textSize);    	
    }
    
    public void onClickTSizeCgangeMidol(View view){
    	
    	int textSize= dispWidth/20;
    	tSizeCgange(textSize);    	
    }
    public void onClickTSizeCgangeMini(View view){
    	
    	int textSize= dispWidth/30;
    	tSizeCgange(textSize);    	
    }
    
    public void tSizeCgange(int textSize){
    	
    	//float 
    	TextView textAnsMsg      = (TextView)findViewById(R.id.textAnsMsg);
    	TextView textBigQuestion = (TextView)findViewById(R.id.textBigQuestion);
    	TextView textAns         = (TextView)findViewById(R.id.textAns);
    	TextView textKeikaJikan  = (TextView)findViewById(R.id.textKeikaJikan);
    	TextView textSeikaiRitu  = (TextView)findViewById(R.id.textSeikaiRitu);
    	TextView textQuestion    = (TextView)findViewById(R.id.textQuestion);
    	TextView textTitle       = (TextView)findViewById(R.id.textTitle);
    	
    	
    	
    	CheckBox cb1 = (CheckBox)findViewById(R.id.check1);
    	CheckBox cb2 = (CheckBox)findViewById(R.id.check2);
    	CheckBox cb3 = (CheckBox)findViewById(R.id.check3);
    	CheckBox cb4 = (CheckBox)findViewById(R.id.check4);
    	CheckBox cb5 = (CheckBox)findViewById(R.id.check5);
    	
    	ImageView line0 = (ImageView)findViewById(R.id.line0);
    	ImageView line1 = (ImageView)findViewById(R.id.line1);
    	ImageView line2 = (ImageView)findViewById(R.id.line2);
    	ImageView line3 = (ImageView)findViewById(R.id.line3);
    	ImageView line4 = (ImageView)findViewById(R.id.line4);
    	ImageView line5 = (ImageView)findViewById(R.id.line5);
    	
    	int linHeight = textSize/2;
    	line0.setMinimumHeight(linHeight);
    	line1.setMinimumHeight(linHeight);
    	line2.setMinimumHeight(linHeight);
    	line3.setMinimumHeight(linHeight);
    	line4.setMinimumHeight(linHeight);
    	line5.setMinimumHeight(linHeight);
    	
    	int width = dispWidth-(int)(dispWidth*0.2);
    	resizeCheckBox(cb1,width,0,textSize);
    	resizeCheckBox(cb2,width,0,textSize);
    	resizeCheckBox(cb3,width,0,textSize);
    	resizeCheckBox(cb4,width,0,textSize);
    	resizeCheckBox(cb5,width,0,textSize);
    	
    	
    	width = dispWidth-(int)(dispWidth*0.1);
    	resizeTextView(textAnsMsg     ,width,0,textSize);
    	resizeTextView(textBigQuestion,width,0,textSize);
    	resizeTextView(textAns        ,width,0,textSize);
    	resizeTextView(textQuestion   ,width,0,textSize);
    	resizeTextView(textSeikaiRitu ,width,0,textSize);
    	resizeTextView(textTitle      ,width,0,textSize);
    	resizeTextView(textKeikaJikan ,width,0,textSize);
    	
    	
    }
    
    
    public void onClickNextQuestion(View view){    	
    	
    	int position = cursor.getPosition()+1;
    	
    	
    	if(position<rowMax){
    		cursor.moveToNext();
    	}
    	else if (position>=rowMax){
    		cursor.moveToPosition(0);
    	}
    	
    	int num = getNextQuestionNum();
    	if(num>0){
    		cursor.moveToPosition(num-1);
    	}
    	
    	onClickButtonInitialization();
    	setQuestion();
    }
    public void onClickBeforQuestion(View view){
    	
    	int position = cursor.getPosition();
    	if(position>0){
    		position=position-1;
    	}
    	cursor.moveToPosition(position);
    	
    	onClickButtonInitialization();
    	setQuestion();
    	
    	
    }
    public void onClickButtonInitialization(){
    	
    	EditText eTextQuestionNum = (EditText)findViewById(R.id.eTextQuestionNum);
    	eTextQuestionNum.setFocusable(false);
    	
    	
    	ScrollView sv =  (ScrollView)findViewById(R.id.ScrollView);
    	
    	sv.scrollTo(0, 0);
    	
    	LinearLayout linearSelectOutLine = (LinearLayout)findViewById(R.id.linearSelectOutLine);
    	
    	linearSelectOutLine.setMinimumHeight(0);
    	linearSelectOutLine.setBackgroundResource(R.color.brack);
    	linearSelectOutLine.setBackgroundResource(R.color.yellow);
    	
    	eTextQuestionNum.setFocusable(true);
    	eTextQuestionNum.setFocusableInTouchMode(true);
    	
    	TextView textAnsMsg = (TextView)findViewById(R.id.textAnsMsg);
    	textAnsMsg.setText("");
    	textAnsMsg.setVisibility(View.GONE);
    	
    	
    	TextView textAns = (TextView)findViewById(R.id.textAns);
    	textAns.setText("");
    	
    	CheckBox cb1 = (CheckBox)findViewById(R.id.check1);
    	CheckBox cb2 = (CheckBox)findViewById(R.id.check2);
    	CheckBox cb3 = (CheckBox)findViewById(R.id.check3);
    	CheckBox cb4 = (CheckBox)findViewById(R.id.check4);
    	CheckBox cb5 = (CheckBox)findViewById(R.id.check5);
    	
    	cb1.setChecked(false);
    	cb2.setChecked(false);
    	cb3.setChecked(false);
    	cb4.setChecked(false);
    	cb5.setChecked(false);
    	
    }
    
    public void onClickReturnMeun(View view){
    	//
    	setContentView(R.layout.main);
    	resizeMain();
    	onDestroy();
    	
    	
    }
    public void onClickEnd(View view){
    	//
    	onDestroy();
    	System.exit(RESULT_OK);
    }
    
   
    /*public void (View view){
    	//    	
    }
    public void (View view){
    	//    	
    }*/
    
    public void setStartTime(){
    	startTime = System.currentTimeMillis();
    }
    
    public void setDispData(){
    	
      	
    	WindowManager windowmanager = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = windowmanager.getDefaultDisplay();
        
        
        dispWidth = disp.getWidth();
        dispHeight = disp.getHeight();
        
        
        if(dispWidth > dispHeight){
        	int temp;
        	temp=dispWidth;
        	dispWidth=dispHeight;
        	dispHeight=temp;	
        }
        
        //dispWidth=500;
        textSize=dispWidth/maxStr;
        
    	DisplayMetrics metrics = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(metrics);  
        dispDensity = metrics.density;
        
        
        
        
    }
    
    public void startQuestion(int in_id){
    	
    	resizeQuestion(textSize);
    	TextView tTitle = (TextView)findViewById(R.id.textTitle);
    	String title = getResources().getString(in_id);
    	tTitle.setText(title);
    	
    	setAdmobs();
    	onClickButtonInitialization();
    	setQuestion();
    	
    }
    
    
    public int getNextQuestionNum(){
    	
    	int num= 0;
    	
    	EditText eTQuestionNum = (EditText)findViewById(R.id.eTextQuestionNum);
    	if(!eTQuestionNum.getText().toString().equals("")){
    	
    		num = Integer.valueOf( eTQuestionNum.getText().toString());
    	}
    	
    	if(num < 0 || num > rowMax ){
    		num=0;
    	}
    	
    	eTQuestionNum.setText("");
    	return num;
    	
    }
    
    
    public void setQuestion(){
    	
    	TextView tBigQuestion = (TextView)findViewById(R.id.textBigQuestion);
    	TextView tQuestion = (TextView)findViewById(R.id.textQuestion);
    	TextView textSpace = (TextView)findViewById(R.id.textSpace);
    	
    	LinearLayout linearImageView =  (LinearLayout)findViewById(R.id.linearImageView);
    	ImageView iv1   = (ImageView)findViewById(R.id.imageView1);
    	ImageView line5   = (ImageView)findViewById(R.id.line5);
    	
    	CheckBox cb1 = (CheckBox)findViewById(R.id.check1);
    	CheckBox cb2 = (CheckBox)findViewById(R.id.check2);
    	CheckBox cb3 = (CheckBox)findViewById(R.id.check3);
    	CheckBox cb4 = (CheckBox)findViewById(R.id.check4);
    	CheckBox cb5 = (CheckBox)findViewById(R.id.check5);
    	//TextView tAnsMsg = (TextView)findViewById(R.id.textAnsMsg);
    	//TextView tErorrMsg = (TextView)findViewById(R.id.textAns);
    	//linerBg.setBackgroundColor(R.color.goldenrod);
    	
    	String qNum        = cursor.getString(1);
    	String bigQuestion = cursor.getString(2);
    	String question    = cursor.getString(3);
    	String gazouPath   = cursor.getString(4);
    	String cb1str      = cursor.getString(5);
    	String cb2str      = cursor.getString(6);
    	String cb3str      = cursor.getString(7);
    	String cb4str      = cursor.getString(8);
    	String cb5str      = cursor.getString(9);
    	int gazouId=0;
    	
    	tBigQuestion.setVisibility(View.GONE);
    	if(!bigQuestion.equals("0")){
    		tBigQuestion.setVisibility(View.VISIBLE);
    		tBigQuestion.setText(bigQuestion);
    		
    		
    	}
    	tQuestion.setText("問 "+qNum+"\n"+question);

    	
//    	int iconId = getResources().getIdentifier("g100_1_17.jpg", "drawable", getPackageName());
    	
    	
   
    	cb1.setText(cb1str);
    	cb2.setText(cb2str);
    	cb3.setText(cb3str);
    	cb4.setText(cb4str);



    	
    	if(cb5str==null){
    		cb5.setVisibility(View.GONE);
    		line5.setVisibility(View.GONE);
    	}else{
    		cb5.setVisibility(View.VISIBLE);
       		cb5.setText(cb5str);
       		line5.setVisibility(View.VISIBLE);
    	}
    	
    	String reGazouPath="";
    	iv1.setVisibility(View.GONE);
    	linearImageView.setVisibility(View.GONE);
    	textSpace.setVisibility(View.GONE);
    	if(gazouPath!=null){
    		for(int i=0;i<gazouPath.length();i++){
    			
    			Character temp = gazouPath.charAt(i);
    			if(temp.equals('.')){
    				reGazouPath = "g"+reGazouPath;
    				break;
    			}else if(temp.equals('/')){
    				reGazouPath = reGazouPath+"_";
    			}else{
    				reGazouPath = reGazouPath+temp; 
    			}
    		}
    		iv1.setVisibility(View.VISIBLE);
    		linearImageView.setVisibility(View.VISIBLE);
    		textSpace.setVisibility(View.VISIBLE);
    		
    		gazouId=getResources().getIdentifier(reGazouPath, "drawable", getPackageName());
    		iv1.setImageResource(gazouId);
    		iv1.setScaleType(ScaleType.FIT_XY);
    	
    	}
    	
    	
    	//ソフトキーボードが開いていれば閉じる
    	EditText eTQuestionNum = (EditText)findViewById(R.id.eTextQuestionNum);
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(eTQuestionNum.getWindowToken(), 0);
		
    }
    public void getData(int kai, int am_pm ){
    	//データ取得
    	String sql 
    	= "SELECT DISTINCT kanngo._id, q_num,big_question.big_question as big_question ," +
    	  " question, gazou_path ," +  
    	  "choice1, choice2, choice3, choice4, choice5, ans " +
    	  "FROM `kanngo` ,`big_question` where kanngo.big_question = big_question._id  " +
    	  "and  kai   = " + kai + " and  am_pm = " + am_pm + " "+
    	  "ORDER BY q_num";
    	
    	if(cursor!=null){
    		cursor.close();
    	}
    	
    	cursor = db.rawQuery(sql,null);
    	rowMax=cursor.getCount();
    	if (rowMax>0){
    		cursor.moveToFirst();
        }
    	
    	
    }
    
    /*
    public void getData(String table_name ,String where){
    	//データ取得
    	
    	cursor = findData(TABLE_NAME,where);
    	rowMax=cursor.getCount();
    	if (rowMax>0){
    		cursor.moveToFirst();
        }
    	
    }
    private Cursor findData(String TABLE_NAME,String where ) { 
        //データを取得
        return db.query(TABLE_NAME,COLUMNS, where,null,null,null,null);  
    }*/
    
    private void setDatabase() {  
        mDbHelper = new DataBaseHelper(this);   
        try {  
            mDbHelper.createEmptyDataBase();  
            db = mDbHelper.openDataBase();  
        	DBHelper dbHelper=new DBHelper(this);
            db=dbHelper.getWritableDatabase();
            
        } catch (IOException ioe) {  
            throw new Error("Unable to create database");  
        } catch(SQLException sqle){  
            throw sqle;  
        }
    } 
    //データベースヘルパーの定義(1)
    private static class DBHelper extends SQLiteOpenHelper {
        //データベースヘルパーのコンストラクタ(2)
        public DBHelper(Context context) {
            super(context,DB_NAME,null,1);
        }
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO 自動生成されたメソッド・スタブ
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO 自動生成されたメソッド・スタブ
		}
    }
    protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		onDestroy();
		
	}    @Override  
    public void onDestroy() {  
		if(cursor!=null){
			cursor.close();
		}
		
        super.onDestroy();  
    }
	
	public void resizeQuestion(int textSize){
    	setContentView(R.layout.question);
    	LinearLayout linearSelectOutLine = (LinearLayout)findViewById(R.id.linearSelectOutLine);
    	LinearLayout linearButtonOutline = (LinearLayout)findViewById(R.id.linearButtonOutline);
    	LinearLayout linearImageView     = (LinearLayout)findViewById(R.id.linearImageView);
    	
    	TextView textSpace  = (TextView)findViewById(R.id.textSpace);
		
    	int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
		
		
    	Button buttonBeforQuestion = (Button)findViewById(R.id.buttonBeforQuestion);
    	Button buttonEnd           = (Button)findViewById(R.id.buttonEnd);
    	Button buttonKaitou        = (Button)findViewById(R.id.buttonKaitou);
    	Button buttonNextQuestion  = (Button)findViewById(R.id.buttonNextQuestion);
    	Button buttonReturnMeun    = (Button)findViewById(R.id.buttonReturnMeun);
    	Button buttonMove          = (Button)findViewById(R.id.buttonMove);
    	Button buttonTextSizeBig   = (Button)findViewById(R.id.buttonTextSizeBig);
    	Button buttonTextSizeMidol = (Button)findViewById(R.id.buttonTextSizeMidol);
    	Button buttonTextSizeMini  = (Button)findViewById(R.id.buttonTextSizeMini);
    	
    	EditText eTQuestionNum = (EditText)findViewById(R.id.eTextQuestionNum);
    	
    	
    	
    	tSizeCgange( textSize);
    	
    	resizeButton(buttonBeforQuestion,0,0,textSize);
    	resizeButton(buttonKaitou       ,0,0,textSize);
    	resizeButton(buttonEnd          ,0,0,textSize);
    	resizeButton(buttonNextQuestion ,0,0,textSize);
    	resizeButton(buttonReturnMeun   ,0,0,textSize);
    	resizeButton(buttonMove         ,0,0,textSize);
    	resizeButton(buttonTextSizeBig  ,0,0,textSize);
    	resizeButton(buttonTextSizeMidol,0,0,textSize);
    	resizeButton(buttonTextSizeMini ,0,0,textSize);
    	
    	resizeEText(eTQuestionNum,0,0,textSize);
    	
    	int width = dispWidth-(int)(dispWidth*0.1);
    	linearImageView.setLayoutParams(new LinearLayout.LayoutParams(width,WC));
    	
    	
    	int padding = dispWidth/80;
    	linearSelectOutLine.setPadding(padding, padding, padding, padding);
    	linearButtonOutline.setPadding(0, padding*2, 0, 0);
    	linearImageView    .setPadding(padding, padding*2, padding, padding*2);
    	textSpace          .setPadding(0, 0, 0, padding);
    	
    }
	
	
	public void resizeEText(EditText editText,int width, int height, int textSize){
		
		if(width!=0)editText.setWidth(width);
		if(height!=0)editText.setHeight(height);
		if(textSize!=0)editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		int padding = textSize/2;
		editText.setPadding(padding, padding, padding, padding);
    }
	public void resizeButton(Button button, int wide ,int height, int textSize){
    	
		if(wide!=0)button.setWidth(wide);
		if(height!=0)button.setHeight(height);
		if(textSize!=0)button.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		
		int padding = textSize/2;
		button.setPadding(padding, padding, padding, padding);
    
	}
	public void resizeCheckBox(CheckBox checkBox,int width, int height, int textSize){
		if(width!=0)checkBox.setMaxWidth(width);
		if(height!=0)checkBox.setHeight(height);
		if(textSize!=0)checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
			
    }
    
	public void resizeTextView(TextView textView, int width,int height, int textSize){
    
		if(width!=0)textView.setMaxWidth(width);
		if(height!=0)textView.setHeight(height);
		if(textSize!=0)textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		
		int padding = textSize/2;
		textView.setPadding(padding, padding, padding, padding);
		
    }
	
	public void resizeMain(){
    
		Button buttonkako101_1 = (Button)findViewById(R.id.buttonkako101_1);
		Button buttonkako101_2 = (Button)findViewById(R.id.buttonkako101_2);
		Button buttonkako100_2 = (Button)findViewById(R.id.buttonkako100_1);
		Button buttonkako100_1 = (Button)findViewById(R.id.buttonkako100_2);
		Button buttonkako99_1  = (Button)findViewById(R.id.buttonkako99_1);
		
		int wide = (int)(dispWidth*0.9);
		resizeButton(buttonkako101_1,wide,0,textSize);
		resizeButton(buttonkako101_2,wide,0,textSize);
		resizeButton(buttonkako100_1,wide,0,textSize);
		resizeButton(buttonkako100_2,wide,0,textSize);
		resizeButton(buttonkako99_1,wide,0,textSize);
		
    }
	
	
    
}

/*public String strReform(String str,int spaceNum ){
double margin=5;


Paint paint = new Paint();
float tSize =  paint.getTextSize();
int dispWidthTemp= (int)(dispWidth - (tSize*margin)-(tSize*spaceNum));
int strNam = (int)(dispWidthTemp / tSize)/2;

String tempStr="";
for(int i=0 ;i<str.length();i++){
	if(i>0 && i%strNam==0){
		tempStr =tempStr + "\n";
	}
	tempStr =tempStr + str.charAt(i);
	
}
//return tempStr;
return str;
}
*/
