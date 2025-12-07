package model;

public class Course {
    private int id;
    private String name;
    private String description;

    // Constructeur vide
    public Course() {}

    // Constructeur avec paramètres
    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructeur sans id (pour l'ajout)
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "ID: " + id + " | Cours: " + name + " | Description: " + description;
    }
}