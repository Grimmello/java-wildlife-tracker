import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class RangerTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_false() {
    Ranger testRanger = new Ranger("John","541-987-1234");
    assertEquals(true, testRanger instanceof Ranger);
  }

  @Test
  public void getName_animalInstantiatesWithName_John() {
    Ranger testRanger = new Ranger("John","541-987-1234");
    assertEquals("John", testRanger.getName());
  }

  @Test
  public void equals_returnsTrueIfNameIsTheSame_false() {
    Ranger firstRanger = new Ranger("John","541-987-1234");
    Ranger anotherRanger = new Ranger("John","541-987-1234");
    assertTrue(firstRanger.equals(anotherRanger));
  }

  @Test
  public void save_assignsIdToObjectAndSavesObjectToDatabase() {
    Ranger testRanger = new Ranger("John","541-987-1234");
    testRanger.save();
    Ranger savedRanger = Ranger.allRangers().get(0);
    assertEquals(testRanger.getId(), savedRanger.getId());
  }

  @Test
  public void allRangers_returnsallRangersInstancesOfRanger_false() {
    Ranger firstRanger = new Ranger("John","541-987-1234");
    firstRanger.save();
    Ranger secondRanger = new Ranger("Roger","541-987-1234");
    secondRanger.save();
    assertEquals(true, Ranger.allRangers().get(0).equals(firstRanger));
    assertEquals(true, Ranger.allRangers().get(1).equals(secondRanger));
  }

  @Test
  public void find_returnsRangerWithSameId_secondRanger() {
    Ranger firstRanger = new Ranger("John","541-987-1234");
    firstRanger.save();
    Ranger secondRanger = new Ranger("Roger","541-987-1234");
    secondRanger.save();
    assertEquals(Ranger.find(secondRanger.getName()), secondRanger);
  }

  @Test
  public void delete_deletesRangerFromDatabase_0() {
    Ranger testRanger = new Ranger("John","541-987-1234");
    testRanger.save();
    testRanger.delete();
    assertEquals(0, Ranger.allRangers().size());
  }

  public void updateName_updatesRangerNameInDatabase_String() {
    Ranger testRanger = new Ranger("John","541-987-1234");
    testRanger.save();
    testRanger.update("Buck", "541-987-1234");
    assertEquals("Buck", testRanger.getName());
  }

  @Test
  public void find_returnsNullWhenNoRangerFound_null() {
    assertTrue(Ranger.find("999") == null);
  }

}
