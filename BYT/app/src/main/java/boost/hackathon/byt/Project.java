package boost.hackathon.byt;

/**
 * Created by Raikuro on 04/03/2015.
 */
public class Project {

    private String name;
    private String description;
    private String owner;
    private String[] tags;
    private String[] users;

    public Project (String project, String owner){

        this.name = project;
        this.owner = owner;

    }

    public Project (String project, String owner, String description, String tags[], String users[]){

        this.name = project;
        this.owner = owner;
        this.description = description;
        this.tags = tags;
        this.users = users;

    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription () { return description; }

    public String [] getTags () { return tags; }

    public String [] getUsers () { return users; }

}
