package gamel

import scala.swing._
import scala.collection.mutable.{Map, HashMap}

abstract class GamelScene extends GamelEntity {

  var loaded = false
  /**
   * FIELDS:
   *  + name - Entity
   * */

  /**
   * Every child class of Entity will want to
   * override this method if it hopes to
   * actually render some stuff on the screen
   * */
  override def draw(g: Graphics2D): Unit = {
    // call parent draw method
    super.draw(g)
  }

  def gives(inst: Symbol): GamelInstance = {
    if (inst == null)
      throw new IllegalArgumentException("instance is null")

    if (inst == name)
      throw new IllegalArgumentException("cannot give self")

    if (!(objects contains inst))
      throw new UndefinedInstanceException("instance " + inst + " is not one of " + name + "'s objects")

    // lazy evaluation
    var obj: GamelInstance = null
    if(objects(inst) != null){
      obj = objects(inst)
    } else if (global.entities contains inst) {
      obj = global.entities(inst)
    } else {
      throw new UndefinedInstanceException("instance " + inst + " is not one of " + name + "'s objects")
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

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *
 *    create a new scene ...
 *
 * */
class scene extends GamelScene { }
