package com.gmail.farachan.d20gm;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.gmail.farachan.d20gm.object.PlayerCharacter;
import com.gmail.farachan.d20gm.object.Skill;

/**
 * @author dumptruckman
 */
public class CharacterList extends ListActivity {

    public static PlayerCharacter CREATE = new PlayerCharacter("Create Character...");

    private static CharacterList instance;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        instance = this;

        setListAdapter(MainScreen.getInstance().getCharacterList());

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
               /* String characterName = parent.getAdapter().getItem(position).toString();
                PlayerCharacter character;
                if (skillName.equals(CREATE)) {
                    skill = new Skill("", true);
                } else {
                    skill = MainScreen.getInstance().getDb().getSkill(skillName);
                    if (skill == null) {
                        android.util.Log.e("D20GM", "Skill not in database!");
                        skill = new Skill("", true);
                    }
                }
                MainScreen.getInstance().showSkill(skill);
                instance.finish();*/
            }
        });
    }
}
