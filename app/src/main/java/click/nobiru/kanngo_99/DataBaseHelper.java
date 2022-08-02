package click.nobiru.kanngo_99;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {  
  
    //The Android �̃f�t�H���g�ł̃f�[�^�x�[�X�p�X  
    private static String DB_PATH = "/data/data/at_sys.pk.kanngo_kokushi/databases/";  
    private static String DB_NAME = "kanngo_kokushi.db";  
    private static String DB_NAME_ASSET = "kanngo_kokushi.db";  
   
    private SQLiteDatabase mDataBase;   
   
    private final Context mContext;  
      
      
    public DataBaseHelper(Context context) {  
        super(context, DB_NAME, null, 1);    
        this.mContext = context;  
    }  
  
    /** 
     * asset �Ɋi�[�����f�[�^�x�[�X���R�s�[���邽�߂̋�̃f�[�^�x�[�X���쐬���� 
     *  
     **/  
    public void createEmptyDataBase() throws IOException{  
        boolean dbExist = checkDataBaseExists();  
  
        if(dbExist){  
            // ���łɃf�[�^�x�[�X�͍쐬����Ă���  
        }else{  
            // ���̃��\�b�h���ĂԂ��ƂŁA��̃f�[�^�x�[�X��  
            // �A�v���̃f�t�H���g�V�X�e���p�X�ɍ����  
            this.getReadableDatabase();  
   
            try {  
                // asset �Ɋi�[�����f�[�^�x�[�X���R�s�[����  
                copyDataBaseFromAsset();   
            } catch (IOException e) {  
                throw new Error("Error copying database");  
            }  
        }  
    }   
      
    /** 
     * �ăR�s�[��h�~���邽�߂ɁA���łɃf�[�^�x�[�X�����邩�ǂ������肷�� 
     *  
     * @return ���݂��Ă���ꍇ {@code true} 
     */  
    private boolean checkDataBaseExists() {  
        SQLiteDatabase checkDb = null;  
   
        try{  
            String dbPath = DB_PATH + DB_NAME;  
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);  
        }catch(SQLiteException e){  
            // �f�[�^�x�[�X�͂܂����݂��Ă��Ȃ�  
        }  
   
        if(checkDb != null){  
            checkDb.close();  
        }  
        return checkDb != null ? true : false;  
    }      
   
    /** 
     * asset �Ɋi�[�����f�[���x�[�X���f�t�H���g�� 
     * �f�[�^�x�[�X�p�X�ɍ쐬��������̃f�[�^�x�[�X�ɃR�s�[���� 
     * */  
    private void copyDataBaseFromAsset() throws IOException{  
   
        // asset ���̃f�[�^�x�[�X�t�@�C���ɃA�N�Z�X  
        InputStream mInput = mContext.getAssets().open(DB_NAME_ASSET);  
   
        // �f�t�H���g�̃f�[�^�x�[�X�p�X�ɍ쐬�������DB  
        String outFileName = DB_PATH + DB_NAME;  
   
        OutputStream mOutput = new FileOutputStream(outFileName);  
  
        // �R�s�[  
        byte[] buffer = new byte[1024];  
        int size;  
        while ((size = mInput.read(buffer)) > 0){  
            mOutput.write(buffer, 0, size);  
        }  
   
        //Close the streams  
        mOutput.flush();  
        mOutput.close();  
        mInput.close();  
    }  
   
    public SQLiteDatabase openDataBase() throws SQLException{  
        //Open the database  
        String myPath = DB_PATH + DB_NAME;  
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);  
        return mDataBase;  
    }  
      
    @Override  
    public void onCreate(SQLiteDatabase arg0) {  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
    }  
  
    @Override  
    public synchronized void close() {  
        if(mDataBase != null)  
            mDataBase.close();  
      
        super.close();  
    }  
}  

