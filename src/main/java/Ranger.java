import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Ranger {
  private int id;
  private String name;
  private String phoneNumber;

  public Ranger(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rangers (name, phoneNumber) VALUES (:name, :phoneNumber);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("phoneNumber", this.phoneNumber)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String newName, String newNumber) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET (name=:name, phoneNumber=:phoneNumber) WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("name", newName)
        .addParameter("phoneNumber", newNumber)
        .executeUpdate();
    }
  }

  public static List<Ranger> allRangers() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers;";
      return con.createQuery(sql)
        .executeAndFetch(Ranger.class);
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE rangerid=:id;";
        List<Sighting> sightings = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Sighting.class);
      return sightings;
    }
  }

  public static Ranger find(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE name=:name;";
      Ranger ranger = con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetchFirst(Ranger.class);
      return ranger;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM rangers WHERE name=:name;";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherRanger){
    if (!(otherRanger instanceof Ranger)) {
      return false;
    } else {
      Ranger newRanger = (Ranger) otherRanger;
      return this.getName().equals(newRanger.getName()) && this.getId() == newRanger.getId();
    }
  }
}
