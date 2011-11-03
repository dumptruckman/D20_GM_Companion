package com.gmail.farachan.d20gm.object;

import android.widget.Button;
import android.widget.TableRow;
import com.gmail.farachan.d20gm.MainScreen;

/**
 * @author dumptruckman
 */
public class Skill {

    public static String CREATE = "Create Skill...";

    private String name;
    private boolean active;
    private Integer dbId;

    private Button rollButton;
    private Button resultButton;
    private TableRow tableRow;

    public Skill(Integer id, String name) {
        this(id, name, true);
    }

    public Skill(Integer id, String name, boolean active) {
        this.name = name;
        this.active = active;
        this.dbId = id;

        tableRow = new TableRow(MainScreen.getInstance());
        rollButton = new Button(MainScreen.getInstance());
        resultButton = new Button(MainScreen.getInstance());

        rollButton.setText(getName());
        resultButton.setText("Last Results");
    }

    public String getName() {
        return name;
    }
    
    public boolean isActive() {
        return active;
    }

    public int getDbId() {
        return dbId;
    }

    public Button getRollButton() {
        return rollButton;
    }

    public Button getResultButton() {
        return resultButton;
    }

    public TableRow getTableRow() {
        return tableRow;
    }

    public boolean equals(Object o) {
        return o instanceof Skill &&
                ((Skill)o).getName().equals(this.getName()) && ((Skill)o).isActive() == this.isActive();
    }

    public String toString() { return getName(); }
}
