package tp.synthese.chatapp_firebase;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {
    public static void saveData(String data, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput("data.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveLastMsg(String data,String ChatId, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput(ChatId+".txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveName(String data, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput("dataName.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void saveEmail(String data, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput("dataEmail.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String getData(Context context){
        String data="";
        try{
            FileInputStream fileInputStream=context.openFileInput("data.txt");
            InputStreamReader isr=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    public static String getName(Context context){
        String data="";
        try{
            FileInputStream fileInputStream=context.openFileInput("dataName.txt");
            InputStreamReader isr=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
    public static String getEmail(Context context){
        String data="";
        try{
            FileInputStream fileInputStream=context.openFileInput("dataEmail.txt");
            InputStreamReader isr=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
    public static String getLastMsg(Context context,String ChatId){
        String data="0";
        try{
            FileInputStream fileInputStream=context.openFileInput(ChatId+".txt");
            InputStreamReader isr=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
