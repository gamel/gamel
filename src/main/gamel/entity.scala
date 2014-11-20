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
  protected[gamel] var name: Symbol = null

  // container for all kinds of stuff
  protected[gamel] var attributes = new HashMap[String, Any]

  // entity's renderer
  protected[gamel] var renderer: GamelRenderer = null

  // entity's action
  protected[gamel] var actions = new HashMap[Symbol, GamelAction]

  // entity's children
  protected[gamel] var objects = new HashMap[Symbol, GamelInstance]

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

  def set(attribute: String, value: Any): Unit = {
    attributes(attribute) = value
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
   * Recursively initializing the entity's renderers
   */ 
  protected[gamel] def initRenderers(): Unit = {
    if (renderer != null)
      renderer.init()

    objects foreach {
      obj => obj._2.initRenderers()
    }
  }

  /**
   * Recursively initializing the entity's objects
   */ 
  protected[gamel] def initObjects(): Unit = {
    objects foreach {
      o => {
        if (o._2 == null && !(global.entities contains o._1)) 
          // objects not found
          throw new UndefinedInstanceException("The object "  + o._1 + " has not been found")
        else {
          val obj = global.entities(o._1)
          objects(o._1) = obj
        }
      }
    }
    objects foreach {
      obj => obj._2.initObjects()
    }
  }

  /**
   * Recursively initializing the entity's actions
   */
  protected[gamel] def initActions(): Unit = {
    actions foreach {
      a => {
        if (a._2 == null && !(global.actions contains a._1))
          // actions not found
          throw new UndefinedActionException("action "  + a._1 + " is undefined")
        else {
          val action = global.actions(a._1)
          actions(a._1) = action
          if (action.init != null) 
            action.init(())
        }
      }
    }
    objects foreach {
      obj => obj._2.initActions()
    }
  }

  /**
   * Recursively trigger this entity's actions
   */
  protected[gamel] def triggerActions(): Unit = {
    actions foreach {
      a => {
        if (a._2.condition != null && a._2.condition(())) {
          a._2.action(this :: List())
        } else if(a._2 == null) {
          throw new UndefinedActionException("action " + a._1 + " is undefined")
        }
      }
    }
    objects foreach {
      obj => obj._2.triggerActions()
    }
  }

  /**
   * entity trigger action by name
   * @param action String
   * @return Unit
   * */
  def does(action: Symbol): GamelAction = {
    if (this.isInstanceOf[GamelType]) {
      throw new UnsupportedOperationException(name + " is not an instance or scene")
    }

    if (!(actions contains action)) {
      throw new UndefinedActionException(action + " is not an action of " + name)
    }

    var act = actions(action)

    if (act == null) {
      if (global.actions contains action) {
        act = global.actions(action)
        actions(action) = act
      } else {
        throw new UndefinedActionException("action " + action + " is undefined")
      }
    }

    act
  }

  def gives(inst: Symbol): GamelInstance = {
    if(!(this.isInstanceOf[GamelInstance] || this.isInstanceOf[GamelScene]))
      throw new UnsupportedOperationException("entity-type cannot give")

    if (inst == null)
      throw new IllegalArgumentException("instance is null")

    if (inst == name)
      throw new IllegalArgumentException("cannot give self")

    if (!(global.entities contains inst))
      throw new UndefinedInstanceException("instance " + inst + "is undefined")

    if (!(objects contains inst))
      throw new UndefinedInstanceException("instance " + inst + " is not one of " + name + "'s objects")

    if (!(nobody isDefined inst))
      throw new IllegalStateException("instance " + inst + " is define but not marked as defined")

    if (!(nobody isUsed inst))
      throw new IllegalStateException("instance " + inst + " is used but not marked as used")

    // lazy evaluation
    var obj: GamelInstance = null
    if(objects(inst) != null){
      obj = objects(inst)
    } else if (global.entities contains inst) {
      obj = global.entities(inst)
    }

    // if the object is already moving, something is wrong
    if (obj.moving){
      throw new IllegalStateException(inst + "is already in the process of being given")
    }

    // remove the object from this instance
    objects remove inst

    // set current status of moving
    obj.moving = true

    // set the new parent to this instance,
    // so that if the "gives" fails, we can
    // give the object back to the parent
    obj.parent = this

    // return the object
    obj
  }

}

// So that the user cannot instantiate a scene as an entity type
protected[gamel] abstract class GamelType extends GamelEntity {}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *
 *    create a new entity ...
 *
 * */
class entity extends GamelType { }
