package com.gmail.farachan.d20gm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gmail.farachan.d20gm.object.PlayerCharacter;
import com.gmail.farachan.d20gm.object.Skill;
import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dumptruckman
 */
public class Database extends SQLiteOpenHelper {
    
    private static final int DATABASE_VERSION = 2;
    private static final String ID = "id";

    private static final String DATABASE_NAME = "D20_GM_Companion";
    private static final String CHARACTER_TABLE_NAME = "characters";
    private static final String CHARACTER_TABLE_COLUMN_NAME = "name";
    private static final String CHARACTER_TABLE_COLUMN_PLAYER = "player";
    private static final String CHARACTER_TABLE_COLUMN_INIT= "initiative";
    private static final String CHARACTER_TABLE_COLUMN_FORT= "fortitude";
    private static final String CHARACTER_TABLE_COLUMN_REF= "reflex";
    private static final String CHARACTER_TABLE_COLUMN_WILL= "will";
    private static final String CHARACTER_TABLE_COLUMN_ACTIVE = "active";
    private static final String CHARACTER_TABLE_CREATE =
                "CREATE TABLE " + CHARACTER_TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY ASC, " +
                CHARACTER_TABLE_COLUMN_NAME + " TEXT, " +
                CHARACTER_TABLE_COLUMN_PLAYER + " TEXT, " +
                CHARACTER_TABLE_COLUMN_INIT + " INTEGER, " +
                CHARACTER_TABLE_COLUMN_FORT + " INTEGER, " +
                CHARACTER_TABLE_COLUMN_REF + " INTEGER, " +
                CHARACTER_TABLE_COLUMN_WILL + " INTEGER, " +
                CHARACTER_TABLE_COLUMN_ACTIVE + " INTEGER " +
                ");";

    private static final String SKILL_TABLE_NAME = "skills";
    private static final String SKILL_TABLE_COLUMN_NAME = "name";
    private static final String SKILL_TABLE_COLUMN_ACTIVE = "active";
    private static final String SKILL_TABLE_CREATE =
                "CREATE TABLE " + SKILL_TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY ASC, " +
                SKILL_TABLE_COLUMN_NAME + " TEXT UNIQUE, " +
                SKILL_TABLE_COLUMN_ACTIVE + " INTEGER " +
                ");";

    private static final String CHARACTER_SKILL_TABLE_NAME = "character_skills";
    private static final String CHARACTER_SKILL_TABLE_COLUMN_CHARACTER = "character_id";
    private static final String CHARACTER_SKILL_TABLE_COLUMN_SKILL = "skill_id";
    private static final String CHARACTER_SKILL_TABLE_COLUMN_SCORE = "score";
    private static final String CHARACTER_SKILL_TABLE_CREATE =
                "CREATE TABLE " + CHARACTER_SKILL_TABLE_NAME + " (" +
                CHARACTER_SKILL_TABLE_COLUMN_CHARACTER + " INTEGER, " +
                CHARACTER_SKILL_TABLE_COLUMN_SKILL + " INTEGER, " +
                CHARACTER_SKILL_TABLE_COLUMN_SCORE + " INTEGER, " +
                "PRIMARY KEY (" + CHARACTER_SKILL_TABLE_COLUMN_CHARACTER + ", " + CHARACTER_SKILL_TABLE_COLUMN_SKILL + ") " +
                ");";

    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CHARACTER_TABLE_CREATE);
        db.execSQL(SKILL_TABLE_CREATE);
        db.execSQL(CHARACTER_SKILL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHARACTER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SKILL_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHARACTER_SKILL_TABLE_NAME);
        this.onCreate(db);
    }

    public List<Skill> getSkills() {
        List<Skill> skills = new ArrayList<Skill>();
        skills.add(SkillList.CREATE);
        String[] columns = {ID, SKILL_TABLE_COLUMN_NAME, SKILL_TABLE_COLUMN_ACTIVE};
        Cursor cursor = getWritableDatabase().query(
                SKILL_TABLE_NAME, columns,
                null, null, null, null, null);
        
        while (cursor.moveToNext()) {
            skills.add(new Skill(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1));
        }
        cursor.close();
        return skills;
    }

    public Skill getSkill(String name) {
        String[] col = {ID, SKILL_TABLE_COLUMN_NAME, SKILL_TABLE_COLUMN_ACTIVE};
        Cursor cursor = getWritableDatabase().query(SKILL_TABLE_NAME, col,
                SKILL_TABLE_COLUMN_NAME + "='" + name + "'",
                null, null, null, null);
        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        System.out.println("Skill active: " + cursor.getInt(1));
        Skill skill = new Skill(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1);
        cursor.close();
        return skill;
    }

    public void saveSkill(Skill skill) {
        System.out.println("Saving skill as active: " + skill.isActive());
        getWritableDatabase().execSQL("REPLACE INTO " +
                SKILL_TABLE_NAME + " (" +
                SKILL_TABLE_COLUMN_NAME + ", " +
                SKILL_TABLE_COLUMN_ACTIVE + ") VALUES (" +
                "'" + skill.getName() + "', " +
                (skill.isActive() ? 1 : 0) + ");");

    }

    public List<PlayerCharacter> getCharacters() {
        List<PlayerCharacter> characters = new ArrayList<PlayerCharacter>();
        characters.add(CharacterList.CREATE);
        String[] columns = {CHARACTER_TABLE_COLUMN_NAME,
                CHARACTER_TABLE_COLUMN_PLAYER,
                CHARACTER_TABLE_COLUMN_INIT,
                CHARACTER_TABLE_COLUMN_FORT,
                CHARACTER_TABLE_COLUMN_REF,
                CHARACTER_TABLE_COLUMN_WILL,
                CHARACTER_TABLE_COLUMN_ACTIVE};
        Cursor cursor = getWritableDatabase().query(
                CHARACTER_TABLE_NAME, columns,
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            characters.add(new PlayerCharacter(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6) == 1));
        }
        cursor.close();
        return characters;
    }
}
