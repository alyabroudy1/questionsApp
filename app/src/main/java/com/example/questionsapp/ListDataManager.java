package com.example.questionsapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListDataManager implements DataManagerControllable{

    static ArrayList<Scenario> scenarios;
    Activity activity;
    static String TAG = "ListDataManager";



    public ListDataManager(Activity activity) {
        scenarios = new ArrayList<>();
        this.activity = activity;
        //this.loadFixtures();
        this.loadJson();
    }

    public List<Scenario> getScenarios(){
        return ListDataManager.scenarios;
    }

    public void saveScenarios(Scenario scenario){
        if (!ListDataManager.scenarios.contains(scenario)){
            ListDataManager.scenarios.add(scenario);
        }
    }

    public void deleteScenarios(Scenario scenario){
        ListDataManager.scenarios.remove(scenario);
    }

    public void updateScenarios(Scenario scenario){
        for (Scenario s : ListDataManager.scenarios){
            if (s == scenario){
                ListDataManager.scenarios.remove(s);
                ListDataManager.scenarios.add(scenario);
            }
        }
    }

    static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    public void loadJson() {
        String jsonFileString = getJsonFromAssets(activity.getApplicationContext(), "ihk.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Scenario>>() {
        }.getType();

        scenarios = gson.fromJson(jsonFileString, listUserType);
    }

}
