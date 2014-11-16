package gamel

abstract class GamelInstance extends GamelEntity {
  var entityType: Symbol = null

  def of(entType: Symbol): Unit = {
    if (entType == null)
      throw new CreationException("entity type cannot be null");
    if (!(global.prototypes contains entType))
      throw new CreationException("entity type is not defined");

    val ent = global.prototypes(entType)

    entityType = entType
    attributes = ent.attributes.clone()
    renderer = ent.renderer
    actions = ent.actions.clone()
    //parent = ent.parent
    objects = ent.objects.clone()
  }

  def gives(inst: Symbol): GamelInstance = {
    if (inst == null)
      throw new IllegalArgumentException("instance is null")

    if (inst == name)
      throw new IllegalArgumentException("cannot give self")

    if (!(objects contains inst))
      throw new UndefinedInstanceException("instance " + inst + " is not one of " + name + "'s objects")

    // lazy evaluation
    if(objects(inst) == null) {
      if (!(global.entities contains inst)) {
        throw new UndefinedInstanceException("instance " + inst + " is not one of " + name + "'s objects")
      } else {
        objects(inst) = global.entities(inst)
      }
    }

    // remove the object from this instance
    // this returns an optional, so we have to use get()
    // to get the value out of the option
    val obj = objects(inst)
    objects remove inst

    // set the new parent to this instance,
    // so that if the "gives" fails, we can
    // give the object back to the parent
    obj.parent = this

    global.entities(name) = this

    // return the object
    obj
  }

  def to(newParent: Symbol): Unit = {
    if (newParent == null) {
      // give this object back to its parent before errororing
      parent.objects(name) = this
      throw new IllegalArgumentException("instance is null")
    }

    if (newParent == name){
      parent.objects(name) = this
      throw new IllegalArgumentException("cannot give entity to itself")
    }

    if (!(global.entities contains newParent) && !(global.scenes contains newParent)){
      parent.objects(name) = this
      throw new UndefinedInstanceException("instance is null")
    }

    if(global.entities contains newParent){
      global.entities(newParent).objects(name) = this
    } else {
      global.scenes(newParent).objects(name) = this
    }

    parent = null
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
