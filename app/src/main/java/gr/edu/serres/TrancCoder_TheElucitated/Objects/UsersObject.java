package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by James Nikolaidis on 11/6/2016.
 */

public class UsersObject {
    public String name,lastname,location;

    public UsersObject(String ...values){
        this.name = values[0];
        this.lastname = values[1];
        this.location = values[2];
    }

}
