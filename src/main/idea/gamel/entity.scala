package idea.gamel

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
  var renderer: (Graphics2D) => Unit = null

  // entity's action
  var actions = new HashMap[String, GamelAction]
  
  // entity's parent
  var parent: GamelEntity = null

  // entity's children
  var children = new HashMap[Symbol, GamelEntity]

  /**
   * Create the flexibility that the user 
   * could put anything in the attributes
   * @param attribute String
   * @param description Any
   * @return Unit
   * */
  def addAttrib(attribute: String, description: Any): Unit = {
    if (attribute == null || description == null)
      throw new IllegalArgumentException("attribute or its name cannot be null")
    attributes(attribute) = description
  }

  def addAction(name: String, action: GamelAction): Unit = {
    if (name == null || action == null)
      throw new IllegalArgumentException("action or its name cannot be null")
    // put action into actions map
    actions(name) = action
  }
  /**
   * Semantics for getting attributes from
   * the attributes - HashMap
   * @param attribute String
   * @return description Any
   * */
  def tell(attribute: String): Any = attributes get attribute

  /**
   * Every child class of Entity will want to
   * OVERRIDE this method if it hopes to 
   * actually render some stuff on the screen
   * @param g Graphics2D
   * @return Unit
   * */
  def draw(g: Graphics2D): Unit = {
    if (renderer == null) return
    renderer(g)
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

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *  create new entity ...
 * */
class entity extends GamelEntity { }
