package models;

public class Group {
    private  String id;
    private final String name;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Group(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){return "" + name + ", " + id;}
}
