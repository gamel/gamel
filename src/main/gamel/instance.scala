package gamel

abstract class GamelInstance extends GamelEntity {
  var entityType: Symbol = null

  // denotes whether this instance is in the process of being moved
  private var moving = false

  def of(entType: Symbol): Unit = {
    if (entType == null)
      throw new CreationException("entity type cannot be null");
    if (!(global.prototypes contains entType))
      throw new CreationException("entity type is not defined");

    val ent = global.prototypes(entType)

    entityType = entType

    if (renderer == null)
      renderer = ent.renderer

    ent.attributes foreach {
      entry => {
        val key = entry._1
        val value = entry._2
        if (!(attributes contains key))
          attributes(key) = value
      }
    }

    ent.actions foreach {
      entry => {
        val key = entry._1
        val value = entry._2
        if (!(actions contains key))
          actions(key) = value
      }
    }

    ent.objects foreach {
      entry => {
        val key = entry._1
        val value = entry._2
        if (!(objects contains key))
          objects(key) = value
      }
    }

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

  def to(newParent: Symbol): Unit = {
    if (newParent == null) {
      // give this object back to its parent before errororing
      parent.objects(name) = this
      moving = false
      throw new IllegalArgumentException("instance is null")
    }

    if (newParent == name){
      parent.objects(name) = this
      moving = false
      throw new IllegalArgumentException("cannot give entity to itself")
    }

    if (!(global.entities contains newParent) && !(global.scenes contains newParent)){
      parent.objects(name) = this
      moving = false
      throw new UndefinedInstanceException("instance is null")
    }

    if (!moving) {
      parent.objects(name) = this
      throw new IllegalStateException("to without gives")
    }

    // parent and newParent must be either both scenes
    // or both entities
    if(global.entities contains newParent){
      if(!(global.entities contains parent)){
        parent.objects(name) = this
        moving = false
        throw new IllegalArgumentException("new parent is not an instance")
      }
      global.entities(newParent).objects(name) = this
    } else {
      if(!(global.scenes contains parent)){
        parent.objects(name) = this
        moving = false
        throw new IllegalArgumentException("new parent is not a scene")
      }
      global.scenes(newParent).objects(name) = this
    }

    parent = null
    moving = false
  }
}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *
 *    create a new instance ...
 *
 * */
class instance extends GamelInstance { }

/**
 * Defines the nobody instance, which gets implicit
 * ownership of every newly created, unowned object.
 *
 * nobody is an entity, but it does not get a symbolic
 * name.
 */
object nobody extends GamelInstance {
  override def to(newParent: Symbol): Unit = {
    throw new IllegalArgumentException("Cannot give nobody");
  }
}
