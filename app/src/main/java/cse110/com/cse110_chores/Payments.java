package cse110.com.cse110_chores;

/**
 * Created by Matthew on 11/10/2015.
 */
public class Payments {
    //priv var
    int _id;
    String owner;
    String ownee;
    String amount;
    String description;
    int groupid;
    boolean found;

    //empty constructor
    public Payments() {
        this.found = false;
    }

    //constructor
    public Payments(int id, String owner, String ownee, String amount, String description, int groupid) {
        this._id = id;
        this.owner = owner;
        this.ownee = ownee;
        this.amount = amount;
        this.description = description;
        this.groupid = groupid;
        this.found = true;
    }

    //constructor
    public Payments(String owner, String ownee, String amount, String description, int groupid) {
        this.owner = owner;
        this.ownee = ownee;
        this.amount = amount;
        this.description = description;
        this.groupid = groupid;
        this.found = true;
    }

    //get id
    public int getId() {
        return this._id;
    }

    //get name
    public String getOwner() {
        return this.owner;
    }

    public String getOwnee() {
        return this.ownee;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    public int getGroupid() {
        return this.groupid;
    }

    public boolean getFound() {
        return this.found;
    }
}
