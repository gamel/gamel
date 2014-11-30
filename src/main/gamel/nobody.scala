package gamel

import scala.collection.mutable.{Map, HashMap}

/**
 * Defines the nobody instance, which gets implicit
 * ownership of every newly created, unowned object.
 *
 * nobody is an entity, but it does not get a symbolic
 * name.
 */
object nobody extends GamelInstance {

  // keep track of all names
  // count of +1 means defined and unused
  //          -1 means used and undefined
  //           0 means defined and used
  protected[gamel] val entities = new HashMap[Symbol, Int]

  protected[gamel] def isDefined(s: Symbol): Boolean =
    (entities contains s) && (entities(s) >= 0)

  protected[gamel] def isUsed(s: Symbol): Boolean =
    (entities contains s) && (entities(s) <= 0)

  protected[gamel] def define(s: Symbol): Unit = {
    if(entities contains s){
      entities(s) += 1
    } else {
      entities(s) = 1
    }
  }

  protected[gamel] def use(s: Symbol): Unit = {
    if(entities contains s){
      entities(s) -= 1
    } else {
      entities(s) = -1
    }
  }

  override def to(newParent: Symbol): Unit = {
    throw new IllegalArgumentException("Cannot give nobody");
  }

  override def gives(inst: Symbol): GamelInstance = {
    if (inst == null)
      throw new IllegalArgumentException("instance is null")

    if (!(global.entities contains inst))
      throw new UndefinedInstanceException("instance " + inst + " is undefined")

    if (!(nobody isDefined inst))
      throw new IllegalStateException("instance " + inst + " is define but not marked as defined")

    if (nobody isUsed inst)
      throw new IllegalArgumentException("instance " + inst + " belongs to someone!")

    var obj = global.entities(inst)

    obj.moving = true

    obj
  }
}
