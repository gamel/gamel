package gamel

import scala.swing._
import scala.collection.mutable.{Map, HashMap}

abstract class GamelEntity extends Drawable {

  /**
   * ESSENTIAL: name of the entity
   * GAMEL will be actively check the
   * validity of the name in define
   * and create syntax
   * */
  var name: Symbol = null

  // container for all kinds of stuff
  var attributes = new HashMap[String, Any]

  // entity's renderer
  var renderer: GamelRenderer = null

  // entity's action
  var actions = new HashMap[Symbol, GamelAction]

  // entity's parent
  protected var parent: GamelEntity = null

  // entity's children
  var objects = new HashMap[Symbol, GamelInstance]

  /**
   * Create the flexibility that the user
   * could put anything in the attributes
   * @param attribute String
   * @param description Any
   * @return Unit
   * */
  def addAttrib(attribute: String, description: Any): Unit = {
    if (attribute == null || description == null)
      throw new DefinitionException("attribute or its name cannot be null")
    attributes(attribute) = description
  }

  /**
   * Semantics for getting attributes from
   * the attributes - HashMap
   * @param attribute String
   * @return description Any
   * */
  def tell(attribute: String): Any = {
    val attr = attributes get attribute
    attr match {
      case None => throw new NullPointerException("the attribute " + attribute + " to find is null")
      case Some(value) => return value
    }
    return null
  }

  /**
   * Every child class of Entity will want to
   * OVERRIDE this method if it hopes to
   * actually render some stuff on the screen
   * @param g Graphics2D
   * @return Unit
   * */
  def draw(g: Graphics2D): Unit = {
    if (renderer == null) return
    val at = g.getTransform();
    renderer.render(this, g)
    g.setTransform(at);
  }

  /**
   * entity trigger action by name
   * @param action String
   * @return Unit
   * */
  def does(action: String): Unit = {
    // trigger action here
    // MISSING()
  }

}

// So that the user cannot instantiate a scene as an entity type
abstract class GamelType extends GamelEntity {}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *
 *    create a new entity ...
 *
 * */
class entity extends GamelType { }
