package com.example.ilya.robocontroller;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    int Fan = 0, Speed = 1,PeriodicRatioInt,IntegralRatioInt,DifferentialRatioInt,FPeriodicRatioInt,FIntegralRatioInt,FDifferentialRatioInt,TurnInt;
    float PeriodicRatio=0, DifferentialRatio=0, IntegralRatio=0;
    float FPeriodicRatio=0, FDifferentialRatio=0, FIntegralRatio=0,Turn=0;
    int FSpeed=1, NormalTime=0,StopTime=0,ReturnTime=0;
    boolean CanUpload=true;
    byte[] sandingArray = new byte[7];

    final int FROM_PHONE = 0;
    final int FROM_ARDUINO = 1;
    final int BT_ENABLING = 0;
    final int BT_ENABLED = 1;
    final int CONNECTING = 2;
    final int CONNECTED = 3;
    final int CONNECTION_FAILED = 4;

    String  FanSave = "Fan",
            SpeedSave = "Speed",
            PeriodicRatioSave = "PeriodicRatio",
            DifferentialRatioSave = "DifferentialRatio",
            IntegralRatioSave = "IntegralRatio",
            FPeriodicRatioSave = "FPeriodicRatio",
            FDifferentialRatioSave = "FDifferentialRatio",
            FIntegralRatioSave = "FIntegralRatio",
            TurnSave = "Turn",
            FSpeedSave = "FSpeed",
            NormalTimeSave = "NormalTime",
            StopTimeSave = "StopTime",
            ReturnTimeSave = "ReturnTime";

    TextView LastSpeed, LastPeriodic, LastDifferential, LastIntegral, LastFan, CurrentFan;
    SeekBar SpeedBar, FanBar;
    EditText SpeedEditText, PeriodicEditText,DifferentialEditText,IntegralEditText, FPeriodicEditText, FDifferentialEditText, FIntegralEditText,FSpeedEditText,NormalTimeEditText,
            TurnEditText, StopTimeEditText,ReturnTimeEditText;
    Button SpeedUndo, SpeedRedo;
    Button PeriodicUndo, PeriodicRedo;
    Button DifferentialUndo, DifferentialRedo;
    Button IntegralUndo, IntegralRedo;
    Button StartDebug, Start, SetData, Console, Stop, Profiles;
    ImageButton AddProfile;
    SharedPreferences SPref;
    BluetoothSocket clientSocket;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SeekBars
        FanBar = (SeekBar) findViewById(R.id.seekBarFan);
        assert FanBar != null;
        FanBar.setOnSeekBarChangeListener(this);
        SpeedBar = (SeekBar) findViewById(R.id.seekBarSpeed);
        assert SpeedBar != null;
        SpeedBar.setOnSeekBarChangeListener(this);

        //Upper Buttons
        SpeedUndo = (Button) findViewById(R.id.SpeedUndoBtn);
        assert SpeedUndo != null;
        SpeedUndo.setOnClickListener(this);
        SpeedRedo = (Button) findViewById(R.id.SpeedRedoBtn);
        assert SpeedRedo != null;
        SpeedRedo.setOnClickListener(this);

        //Middle Buttons
        PeriodicUndo = (Button) findViewById(R.id.periodRatioUdoBtn);
        assert PeriodicUndo != null;
        PeriodicUndo.setOnClickListener(this);
        PeriodicRedo = (Button) findViewById(R.id.periodRatioRedoBtn);
        assert PeriodicRedo != null;
        PeriodicRedo.setOnClickListener(this);
        IntegralUndo = (Button) findViewById(R.id.integralRatioUndoBtn);
        assert IntegralUndo != null;
        IntegralUndo.setOnClickListener(this);
        IntegralRedo = (Button) findViewById(R.id.integralRatioRedoBtn);
        assert IntegralRedo != null;
        IntegralRedo.setOnClickListener(this);
        DifferentialUndo = (Button) findViewById(R.id.diffRatioUdoBtn);
        assert DifferentialUndo != null;
        DifferentialUndo.setOnClickListener(this);
        DifferentialRedo = (Button) findViewById(R.id.diffRatioRedoBtn);
        assert DifferentialRedo != null;
        DifferentialRedo.setOnClickListener(this);

        //Lower Buttons
        StartDebug = (Button) findViewById(R.id.startDebugBtn);
        assert StartDebug != null;
        StartDebug.setOnClickListener(this);
        Start = (Button) findViewById(R.id.startBtn);
        assert Start != null;
        Start.setOnClickListener(this);
        SetData = (Button) findViewById(R.id.setdataBtn);
        assert SetData != null;
        SetData.setOnClickListener(this);
        Console = (Button) findViewById(R.id.consoleBtn);
        assert Console != null;
        Console.setOnClickListener(this);
        Stop = (Button) findViewById(R.id.stopBtn);
        assert Stop != null;
        Stop.setOnClickListener(this);
        Profiles = (Button) findViewById(R.id.profileBtn);
        assert Profiles != null;
        Profiles.setOnClickListener(this);
        AddProfile = (ImageButton) findViewById(R.id.profileAddBtn);
        assert AddProfile != null;
        AddProfile.setOnClickListener(this);

        //EditTexts
        SpeedEditText = (EditText) findViewById(R.id.editSpeed);
        PeriodicEditText = (EditText) findViewById(R.id.editPeriodRatio);
        DifferentialEditText = (EditText) findViewById(R.id.editDifferRatio);
        IntegralEditText = (EditText) findViewById(R.id.editIntegralRatio);
        FPeriodicEditText = (EditText) findViewById(R.id.editFPerRatio);
        FDifferentialEditText = (EditText) findViewById(R.id.editFDifRatio);
        FIntegralEditText = (EditText) findViewById(R.id.editFIntRatio);
        TurnEditText = (EditText) findViewById(R.id.editTurn);
        FSpeedEditText = (EditText) findViewById(R.id.editFSpeed);
        NormalTimeEditText = (EditText) findViewById(R.id.editNormapTime);
        StopTimeEditText = (EditText) findViewById(R.id.editStopTime);
        ReturnTimeEditText = (EditText) findViewById(R.id.editReturnTime);

        //TextViews
        LastSpeed = (TextView) findViewById(R.id.lastSpeedTextView);
        LastPeriodic = (TextView) findViewById(R.id.lastPeriodic);
        LastDifferential = (TextView) findViewById(R.id.lastDiff);
        LastIntegral = (TextView) findViewById(R.id.lastIntegral);
        LastFan = (TextView) findViewById(R.id.lastFan);
        CurrentFan = (TextView) findViewById(R.id.fanTxtViw);

        SPref = getPreferences(MODE_PRIVATE);
        //Setting values to variables from memory
        Fan = SPref.getInt(FanSave,0);
        Speed = SPref.getInt(SpeedSave,1);
        PeriodicRatio = SPref.getFloat(PeriodicRatioSave,0);
        DifferentialRatio = SPref.getFloat(DifferentialRatioSave,0);
        IntegralRatio = SPref.getFloat(IntegralRatioSave,0);
        FPeriodicRatio = SPref.getFloat(FPeriodicRatioSave,0);
        FDifferentialRatio = SPref.getFloat(FDifferentialRatioSave,0);
        FIntegralRatio = SPref.getFloat(FIntegralRatioSave,0);
        Turn = SPref.getFloat(TurnSave,0);
        FSpeed = SPref.getInt(FSpeedSave,0);
        NormalTime = SPref.getInt(NormalTimeSave,0);
        StopTime = SPref.getInt(StopTimeSave,0);
        ReturnTime = SPref.getInt(ReturnTimeSave,0);

        //Setting values to Edittexts and Hotbars
        FanBar.setProgress(Fan);
        SpeedEditText.setText(String.valueOf(Speed));
        SpeedBar.setProgress(Speed-1);
        PeriodicEditText.setText(String.valueOf(PeriodicRatio));
        DifferentialEditText.setText(String.valueOf(DifferentialRatio));
        IntegralEditText.setText(String.valueOf(IntegralRatio));
        FPeriodicEditText.setText(String.valueOf(FPeriodicRatio));
        FDifferentialEditText.setText(String.valueOf(FDifferentialRatio));
        FIntegralEditText.setText(String.valueOf(FIntegralRatio));
        TurnEditText.setText(String.valueOf(Turn));
        FSpeedEditText.setText(String.valueOf(FSpeed));
        NormalTimeEditText.setText(String.valueOf(NormalTime));
        StopTimeEditText.setText(String.valueOf(StopTime));
        ReturnTimeEditText.setText(String.valueOf(ReturnTime));

        Stop.setEnabled(false);

        handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg){
                /*new ConsoleActivity();
                Chandler = ConsoleActivity.handler;
                Message Cmsg;
                Cmsg = Chandler.obtainMessage(msg.what,msg.obj);
                Chandler.sendMessage(Cmsg);*/
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.consoleBtn: //Opening a Console
                Intent StartConsole = new Intent(this,ConsoleActivity.class);
                startActivity(StartConsole);
                break;
            case R.id.profileBtn: //Opening a profiles list
                Intent OpenProfiles = new Intent(this,ProfileActivity.class);
                startActivity(OpenProfiles);
                break;
            case R.id.setdataBtn: //Проверка значений и отправка
                try {//Попытка чтения значений с полей ввода и одобрения на загрузку
                    Fan = FanBar.getProgress();
                    Speed = Integer.valueOf(String.valueOf(SpeedEditText.getText().toString()));
                    ReturnTime = Integer.valueOf(ReturnTimeEditText.getText().toString());
                    FSpeed = Integer.valueOf(FSpeedEditText.getText().toString());
                    NormalTime = Integer.valueOf(NormalTimeEditText.getText().toString());
                    StopTime = Integer.valueOf(StopTimeEditText.getText().toString());
                    Turn = Float.valueOf(TurnEditText.getText().toString());
                    FPeriodicRatio = Float.valueOf(FPeriodicEditText.getText().toString());
                    FDifferentialRatio = Float.valueOf(FDifferentialEditText.getText().toString());
                    FIntegralRatio = Float.valueOf(FIntegralEditText.getText().toString());
                    PeriodicRatio = Float.valueOf(PeriodicEditText.getText().toString());
                    DifferentialRatio = Float.valueOf(DifferentialEditText.getText().toString());
                    IntegralRatio = Float.valueOf(IntegralEditText.getText().toString());

                    CanUpload=true; //Разрешение на загрузку
                }catch (Exception e){
                    CanUpload=false;//Запрет на загрузку при ошибке чтения
                    Toast.makeText(this,R.string.ValuesReadingError,Toast.LENGTH_LONG).show();
                    return;
                }

                if (Speed>100){
                    SpeedEditText.setText(R.string.MaxSpeed);
                    Speed = 100;
                }
                if (Speed<1){
                    SpeedEditText.setText(R.string.MinSpeed);
                    Speed = 1;
                }
                SpeedBar.setProgress(Speed-1);

                if (FSpeed < 0){
                    FSpeed = 1;
                    FSpeedEditText.setText(R.string.MinSpeed);
                }
                if (FSpeed >100) {
                    FSpeed = 100;
                    FSpeedEditText.setText(R.string.MaxSpeed);
                }

                if (Turn > 1){
                    Turn = 1;
                    TurnEditText.setText("1");
                }
                if (CanUpload){
                    Saver();
                    Intent StartConsole1 = new Intent(this,ConsoleActivity.class);
                    startActivity(StartConsole1);
                    Uploader();
                }
                break;
            case R.id.startBtn:
                Switcher(false);
                break;
            case R.id.startDebugBtn:
                Intent StartConsoleByDebug = new Intent(this,ConsoleActivity.class);
                startActivity(StartConsoleByDebug);
                Switcher(false);
                break;
            case R.id.stopBtn:
                Switcher(true);
                break;
        }
    }

    public void Uploader() {
        PeriodicRatioInt = Math.round(PeriodicRatio*1000);
        IntegralRatioInt = Math.round(IntegralRatio*1000);
        DifferentialRatioInt = Math.round(DifferentialRatio*1000);
        FPeriodicRatioInt = Math.round(FPeriodicRatio*1000);
        FIntegralRatioInt = Math.round(FIntegralRatio*1000);
        FDifferentialRatioInt = Math.round(FDifferentialRatio*1000);
        TurnInt = Math.round(Turn*1000);
        new SandingDataIoBackground().execute();
    }

    private void Saver() {
        SPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = SPref.edit();
        editor.putInt(SpeedSave,Speed);
        editor.putInt(FanSave,Fan);
        editor.putFloat(PeriodicRatioSave,PeriodicRatio);
        editor.putFloat(DifferentialRatioSave,DifferentialRatio);
        editor.putFloat(IntegralRatioSave,IntegralRatio);
        editor.putFloat(FPeriodicRatioSave,FPeriodicRatio);
        editor.putFloat(FDifferentialRatioSave,FDifferentialRatio);
        editor.putFloat(FIntegralRatioSave,FIntegralRatio);
        editor.putInt(FSpeedSave,FSpeed);
        editor.putInt(NormalTimeSave,NormalTime);
        editor.putInt(ReturnTimeSave,ReturnTime);
        editor.putFloat(TurnSave,Turn);
        editor.putInt(StopTimeSave,StopTime);
        editor.apply();
    }

    private void Switcher(boolean toCondition) {
        SpeedEditText.setEnabled(toCondition);
        SpeedBar.setEnabled(toCondition);
        SpeedUndo.setEnabled(toCondition);
        SpeedRedo.setEnabled(toCondition);
        PeriodicEditText.setEnabled(toCondition);
        PeriodicUndo.setEnabled(toCondition);
        PeriodicRedo.setEnabled(toCondition);
        DifferentialEditText.setEnabled(toCondition);
        DifferentialUndo.setEnabled(toCondition);
        DifferentialRedo.setEnabled(toCondition);
        IntegralEditText.setEnabled(toCondition);
        IntegralUndo.setEnabled(toCondition);
        IntegralRedo.setEnabled(toCondition);
        FanBar.setEnabled(toCondition);
        FPeriodicEditText.setEnabled(toCondition);
        FDifferentialEditText.setEnabled(toCondition);
        FIntegralEditText.setEnabled(toCondition);
        FSpeedEditText.setEnabled(toCondition);
        NormalTimeEditText.setEnabled(toCondition);
        ReturnTimeEditText.setEnabled(toCondition);
        TurnEditText.setEnabled(toCondition);
        StopTimeEditText.setEnabled(toCondition);
        Profiles.setEnabled(toCondition);
        AddProfile.setEnabled(toCondition);
        StartDebug.setEnabled(toCondition);
        Start.setEnabled(toCondition);
        SetData.setEnabled(toCondition);
        Stop.setEnabled(!toCondition);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //Изменение текущего значения скорости робота и импеллера с помощью движка
        switch (seekBar.getId()){
            case R.id.seekBarSpeed:
                Speed = progress+1;
                SpeedEditText.setText(String.valueOf(Speed));
                break;
            case R.id.seekBarFan:
                Fan = progress;
                CurrentFan.setText("Fan: " + String.valueOf(Fan));
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Изменение строк последних значений скоростей робота и импеллера
        switch (seekBar.getId()){
            case R.id.seekBarSpeed:
                LastSpeed.setText("last: "+String.valueOf(Speed));
                break;
            case R.id.seekBarFan:
                LastFan.setText("last: " + String.valueOf(Fan));
                break;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private byte[] PackerInt(int a){
        byte varb;
        for (int i=6;i>=0;i--){
            varb=Byte.parseByte(String.valueOf(a%10));
            sandingArray[i]=varb;
            a=a/10;
        }
        //sandingArray[6]=100;
        return sandingArray;
    }

    @SuppressLint("StaticFieldLeak")
    class SandingDataIoBackground extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            Byte inputMessage;
            Message msg;
            //BTEnabling
            //NewMessage newMessage = new NewMessage(FROM_PHONE,"Enabling BT...",0);

            /*msg = handler.obtainMessage(FROM_PHONE, "Enabling BT...");
            handler.sendMessage(msg);*/

            String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
            startActivityForResult(new Intent(enableBT), 0);
            BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

            /*msg = handler.obtainMessage(FROM_PHONE,new NewMessage(FROM_PHONE,"Bluetooth enabled",0));
            handler.sendMessage(msg);
            msg = handler.obtainMessage(FROM_PHONE,new NewMessage(FROM_PHONE,"Connecting to Robot...",0));
            handler.sendMessage(msg);*/

            try {
                BluetoothDevice device = bluetooth.getRemoteDevice("20:16:11:17:09:83");
                //Инициируем соединение с устройством
                Method m = device.getClass().getMethod("createRfcommSocket", int.class);

                clientSocket = (BluetoothSocket) m.invoke(device, 1);
                clientSocket.connect();

                /*msg = handler.obtainMessage(FROM_PHONE,new NewMessage(FROM_PHONE,"Connected Successfully!",1));
                handler.sendMessage(msg);*/

                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream();
                outputStream.write('q');
                inputMessage = (byte) inputStream.read();       ///////////////
                outputStream.write(PackerInt(Speed));                        //
                inputMessage = (byte) inputStream.read();                    //
                inputMessage = (byte) inputStream.read();                    //
                outputStream.write(PackerInt(Fan));                          //
                inputMessage = (byte) inputStream.read();                    //
                inputMessage = (byte) inputStream.read();                    //
                outputStream.write(PackerInt(FSpeed));                       //
                inputMessage = (byte) inputStream.read();                    //
                inputMessage = (byte) inputStream.read();                    //    Integer
                outputStream.write(PackerInt(NormalTime));                   //
                inputMessage = (byte) inputStream.read();                    //
                inputMessage = (byte) inputStream.read();                    //
                outputStream.write(PackerInt(ReturnTime));                   //
                inputMessage = (byte) inputStream.read();                    //
                inputMessage = (byte) inputStream.read();                    //
                outputStream.write(PackerInt(StopTime));                     //
                inputMessage = (byte) inputStream.read();                    //
                inputMessage = (byte) inputStream.read();       ///////////////
                outputStream.write(PackerInt(PeriodicRatioInt));       ////////////////
                inputMessage = (byte) inputStream.read();                            //
                inputMessage = (byte) inputStream.read();                            //
                outputStream.write(PackerInt(DifferentialRatioInt));                 //
                inputMessage = (byte) inputStream.read();                            //
                inputMessage = (byte) inputStream.read();                            //
                outputStream.write(PackerInt(IntegralRatioInt));                     //
                inputMessage = (byte) inputStream.read();                            //
                inputMessage = (byte) inputStream.read();                            //
                outputStream.write(PackerInt(FPeriodicRatioInt));                    //
                inputMessage = (byte) inputStream.read();                            //     Float
                inputMessage = (byte) inputStream.read();                            //
                outputStream.write(PackerInt(FDifferentialRatioInt));                //
                inputMessage = (byte) inputStream.read();                            //
                inputMessage = (byte) inputStream.read();                            //
                outputStream.write(PackerInt(FIntegralRatioInt));                    //
                inputMessage = (byte) inputStream.read();                            //
                inputMessage = (byte) inputStream.read();                            //
                outputStream.write(PackerInt(TurnInt));                              //
                inputMessage = (byte) inputStream.read();                            //
                inputMessage = (byte) inputStream.read();              ////////////////
                clientSocket.close();

            }catch (Exception e){
                /*msg = handler.obtainMessage(FROM_PHONE,new NewMessage(FROM_PHONE,"Connection Failed!",2));
                handler.sendMessage(msg);*/
                e.printStackTrace();
            }
            return null;
        }
    }
    class NewMessage{
        private String Message;
        private int From;
        private int Color;
        NewMessage(int from, String message, int SuccessMsg){
            From = from;
            if (from==FROM_PHONE)Message="Ph: ";
            if (from==FROM_ARDUINO)Message="Ar: ";
            Message = Message+message;
            Color = SuccessMsg;
        }
        public String toString(){
            return Message;
        }
    }
}