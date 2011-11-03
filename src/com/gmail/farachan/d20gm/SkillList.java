package com.gmail.farachan.d20gm;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.gmail.farachan.d20gm.object.Skill;
import org.apache.commons.logging.Log;

/**
 * @author dumptruckman
 */
public class SkillList extends ListActivity {

    public static Skill CREATE = new Skill(-1, Skill.CREATE);

    private static SkillList instance;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance = this;

        setListAdapter(MainScreen.getInstance().getSkillList());

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                String skillName = parent.getAdapter().getItem(position).toString();
                Skill skill;
                if (skillName.equals(CREATE)) {
                    skill = new Skill("");
                } else {
                    skill = MainScreen.getInstance().getDb().getSkill(skillName);
                    if (skill == null) {
                        android.util.Log.e("D20GM", "Skill not in database!");
                        skill = new Skill("");
                    }
                }
                MainScreen.getInstance().showSkill(skill);
                instance.finish();
            }
        });
    }
}
