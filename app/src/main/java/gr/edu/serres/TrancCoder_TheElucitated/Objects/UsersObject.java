package gr.edu.serres.TrancCoder_TheElucitated.Objects;

/**
 * Created by James Nikolaidis on 11/6/2016.
 */

public class UsersObject {

    public String name,location,Experience,email;

    public UsersObject(){
 //name=null;

    }

    public UsersObject(String ...values){
        this.name = values[0];
        this.location = values[1];
        this.Experience = values[2];
        this.email = values[3];
    }


    public void SetUserEmail(String email){
        this.email = email;
    }

}
