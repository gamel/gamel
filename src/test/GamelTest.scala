import org.scalatest._
import org.scalatest.Matchers._
import scala.collection.mutable.{Map, HashMap}
import util.Random
import gamel._

class TestGamel extends FlatSpec with Matchers {

  // Tests for define
  "define entity" should "define and return a new Entity object" in {
      val ent = define entity 'Test1
      val ent_type = ent.entity_type
      val ownedEntities = ent.ownedEntities

      ent shouldBe a [Entity]

      ent_type should equal ('Test1)

      ownedEntities shouldBe empty

      gamel.all('Test1) should be (ent)
  }

  it should "throw an exception for multiple definitions" in {
      a [InstantiationException] should be thrownBy {
          define entity 'Test1
      }
  }

  // Tests for has
  "has -" should "create a HasObj with the correct name" in {
      val hasobj = has - 'Test1

      hasobj shouldBe a [HasObj]
  }

  it should "not change the all map" in {
      gamel.all should contain ('Test1 -> Entity('Test1))
      gamel.all('Test1) shouldBe a [Entity]
      gamel.all.size should be (1)
  }

  it should "throw an exception if the entity-type is undefined" in {
      a [ClassNotFoundException] should be thrownBy {
          has - 'Foo
      }
      a [ClassNotFoundException] should be thrownBy {
          has - 'Foo > 'foo
      }
  }

  it should "throw an exception if the entity-type is not an Entity" in {
      gamel.all('Test) = new EntityInstance(new Entity('Test1))

      a [ClassNotFoundException] should be thrownBy {
          has - 'Test
      }
      a [ClassNotFoundException] should be thrownBy {
        has - 'Test > 'test
      }

      // clean up after test
      gamel.all remove 'Test
  }

  "HasObj.>" should "add the instance name to the HasObj" in {
      val hasobj = has - 'Test1

      hasobj.name should equal (null)

      val ret = hasobj > 'test5

      ret should equal (hasobj)

      hasobj.name should equal ('test5)
  }

  it should "not change the all map" in {
      gamel.all should contain ('Test1 -> Entity('Test1))
      gamel.all('Test1) shouldBe a [Entity]
      gamel.all.size should be (1)
  }

  // Tests for action
  // Note that it is ok for names of actions to be the same as the
  // name of an entity or scene, since the syntax is unambiguous
  "action -" should "create an ActionObj with the correct name" in {
      val actobj = action - 'Foo

      actobj.name should equal ('Foo)
  }

  it should "not change the all map" in {
      gamel.all should contain ('Test1 -> Entity('Test1))
      gamel.all('Test1) shouldBe a [Entity]
      gamel.all.size should be (1)
  }

  ":=" should "add a list of functions to an ActionObj" in {
      val actobj = action - 'Foo

      actobj.action_funcs shouldBe empty

      actobj := ()
      actobj.action_funcs shouldBe empty

      var testvar = 0
      def f = {a: Any => testvar = a.asInstanceOf[Int]; print("")}
      actobj := (f)

      // check for the same function:
      val f1 = actobj.action_funcs(0)

      (0 to 100).toList foreach {a: Any => f1(a)
          testvar should be (a.asInstanceOf[Int])}

      actobj.action_funcs.size should be (1)
  }

  // Tests for as
  "Entity.as" should "not add any properties to an Entity that it is not given" in {
      val ent = define entity 'Test2

      ent.ownedEntities shouldBe empty
  }

  it should "not change the all map" in {
      gamel.all should contain ('Test1 -> Entity('Test1))
      gamel.all should contain ('Test2 -> Entity('Test2))

      gamel.all('Test1) shouldBe a [Entity]
      gamel.all('Test2) shouldBe a [Entity]

      gamel.all.size should be (2)
  }

  it should "add the passed properties to the given Entity" in {
      val ent = define entity 'Test3

      val ownedEntities1 = ent.ownedEntities

      ownedEntities1 shouldBe empty

      ent as (
        has - 'Test1 > 'test1,
        has - 'Test2 > 'test2
      )

      val ownedEntities2 = ent.ownedEntities

      ownedEntities2 should contain ('test1 -> HasObj('Test1))
      ownedEntities2 should contain ('test2 -> HasObj('Test2))
  }

  it should "throw an exception for multiple definitions" in {
      val ent = define entity 'Test4

      a [InstantiationException] should be thrownBy {
          ent as (
              has - 'Test1 > 'test1,
              has - 'Test1 > 'test1
          )
      }
  }

}
