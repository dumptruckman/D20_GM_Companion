package com.gmail.farachan.d20gm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.gmail.farachan.d20gm.object.PlayerCharacter;
import com.gmail.farachan.d20gm.object.Skill;

import java.util.ArrayList;

public class MainScreen extends Activity
{

    private ArrayAdapter<Skill> skillList;
    private ArrayAdapter<PlayerCharacter> characterList;
    private boolean skillActive = true;
    private boolean characterActive = true;

    private Skill currentSkill = null;

    //private List<RollerItem> rollers;

    private static MainScreen instance;

    Database db;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.main);

        db = new Database(this.getApplicationContext());
        skillList = new ArrayAdapter<Skill>(this, R.layout.list_item, db.getSkills());
        characterList = new ArrayAdapter<PlayerCharacter>(this, R.layout.list_item, db.getCharacters());

        Button button;

        button = (Button)findViewById(R.id.button_Roller);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = findViewById(R.id.screen_Roller);
                if (view.getVisibility() == View.VISIBLE)
                    view.setVisibility(View.GONE);
                else {
                    setupRoller();
                    findViewById(R.id.screen_CharacterManager).setVisibility(View.GONE);
                    findViewById(R.id.screen_SkillManager).setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
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



        button = (Button)findViewById(R.id.button_SkillSave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.edit_SkillSheetName);
                String skillName = editText.getText().toString();
                if (editText.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), R.string.empty_name_warn,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Button button = (Button)findViewById(R.id.button_SkillActivate);
                    Skill newSkill = new Skill(skillName, button.getText().equals("Active"));
                    updateSkill(newSkill);
                    findViewById(R.id.SkillSheet).setVisibility(View.GONE);
                }
            }
        });

        button = (Button)findViewById(R.id.button_SkillActivate);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button button = (Button)findViewById(R.id.button_SkillActivate);
                if (button.getText().equals("Active")) {
                    button.setText("Inactive");
                    button.setTextColor(android.graphics.Color.RED);
                } else {
                    button.setText("Active");
                    button.setTextColor(android.graphics.Color.GREEN);
                }
            }
        });



        button = (Button)findViewById(R.id.button_CharacterSave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.edit_CharacterSheetName);
                if (editText.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), R.string.empty_name_warn,
                            Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        button = (Button)findViewById(R.id.button_CharacterActivate);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button button = (Button)findViewById(R.id.button_CharacterActivate);
                if (button.getText().equals("Active")) {
                    button.setText("Inactive");
                    button.setTextColor(android.graphics.Color.RED);
                } else {
                    button.setText("Active");
                    button.setTextColor(android.graphics.Color.GREEN);
                }
            }
        });
    }

    public ArrayAdapter<Skill> getSkillList() {
        return skillList;
    }

    public ArrayAdapter<PlayerCharacter> getCharacterList() {
        return characterList;
    }

    public static MainScreen getInstance() {
        return instance;
    }

    public void showSkill(Skill skill) {
        View skillSheet = findViewById(R.id.SkillSheet);
        Button button = (Button)findViewById(R.id.button_SkillActivate);

        if (!skill.isActive()) {
            button.setText("Inactive");
            button.setTextColor(android.graphics.Color.RED);
        } else {
            button.setText("Active");
            button.setTextColor(android.graphics.Color.GREEN);
        }
        EditText editText = (EditText)findViewById(R.id.edit_SkillSheetName);
        editText.setText(skill.getName());
        skillSheet.setVisibility(View.VISIBLE);

        currentSkill = skill;
    }

    public Database getDb() {
        return db;
    }

    public void setupRoller() {
        TableLayout table = (TableLayout)findViewById(R.id.table_Roller);
    }

    public void updateSkill(Skill skill) {
        if (!skill.equals(currentSkill)) {
            // Remove current skill
            skillList.remove(currentSkill);

        }
        db.saveSkill(skill);
        skillList.add(skill);
    }

    public void removeSkill(Skill skill) {

    }
}
