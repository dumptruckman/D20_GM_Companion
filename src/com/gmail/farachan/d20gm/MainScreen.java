package com.gmail.farachan.d20gm;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainScreen extends Activity
{

    private static ArrayAdapter<String> skillList;
    private static ArrayAdapter<String> characterList;
    private static boolean skillActive = true;

    Database db;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new Database(this.getApplicationContext());
        skillList = new ArrayAdapter<String>(this, R.layout.list_item, db.getSkills());
        characterList = new ArrayAdapter<String>(this, R.layout.list_item, db.getCharacters());

        Button button;

        button = (Button)findViewById(R.id.button_Roller);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = findViewById(R.id.screen_Roller);
                if (view.getVisibility() == View.VISIBLE)
                    view.setVisibility(View.GONE);
                else {
                    view.setVisibility(View.VISIBLE);
                    findViewById(R.id.screen_CharacterManager).setVisibility(View.GONE);
                    findViewById(R.id.screen_SkillManager).setVisibility(View.GONE);
                }
            }
        });

        button = (Button)findViewById(R.id.button_CharacterManager);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = findViewById(R.id.screen_CharacterManager);
                if (view.getVisibility() == View.VISIBLE)
                    view.setVisibility(View.GONE);
                else {
                    view.setVisibility(View.VISIBLE);
                    findViewById(R.id.screen_Roller).setVisibility(View.GONE);
                    findViewById(R.id.screen_SkillManager).setVisibility(View.GONE);
                }
            }
        });

        button = (Button)findViewById(R.id.button_SkillManager);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = findViewById(R.id.screen_SkillManager);
                if (view.getVisibility() == View.VISIBLE)
                    view.setVisibility(View.GONE);
                else {
                    view.setVisibility(View.VISIBLE);
                    findViewById(R.id.screen_CharacterManager).setVisibility(View.GONE);
                    findViewById(R.id.screen_Roller).setVisibility(View.GONE);
                }
            }
        });

        button = (Button)findViewById(R.id.button_SelectSkill);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainScreen.this, SkillList.class);
                MainScreen.this.startActivity(myIntent);
            }
        });

        button = (Button)findViewById(R.id.button_SelectCharacter);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainScreen.this, CharacterList.class);
                MainScreen.this.startActivity(myIntent);
            }
        });


        button = (Button)findViewById(R.id.button_SkillActivate);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button button = (Button)findViewById(R.id.button_SkillActivate);
                if (skillActive) {
                    button.setText("Inactive");
                    button.setTextColor(R.color.inactive_red);
                    //button.setTextColor(0xCC0000);
                    skillActive = !skillActive;
                } else {
                    button.setText("Active");
                    button.setTextColor(ColorStateList.valueOf(R.color.active_green));
                    skillActive = !skillActive;
                    //button.sc
                }
            }
        });
    }

    public static ArrayAdapter<String> getSkillList() {
        return skillList;
    }

    public static ArrayAdapter<String> getCharacterList() {
        return characterList;
    }
}
