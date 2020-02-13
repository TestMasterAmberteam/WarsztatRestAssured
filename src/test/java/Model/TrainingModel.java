package Model;

public class TrainingModel {
    private String name;
    private String trainer;
    private String place;
    private int maxParticipants;
    private int price;

    public String getName() {
        return name;
    }

    public TrainingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getTrainer() {
        return trainer;
    }

    public TrainingModel setTrainer(String trainer) {
        this.trainer = trainer;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public TrainingModel setPlace(String place) {
        this.place = place;
        return this;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public TrainingModel setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public TrainingModel setPrice(int price) {
        this.price = price;
        return this;
    }


}
