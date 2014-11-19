package gamel

abstract class GamelInstance extends GamelEntity {
  var entityType: Symbol = null

  // denotes whether this instance is in the process of being moved
  protected[gamel] var moving = false

  // entity's parent
  protected[gamel] var parent: GamelEntity = null


  def of(entType: Symbol): Unit = {
    if (entType == null)
      throw new CreationException("entity type cannot be null");

    if (!(global.prototypes contains entType))
      throw new CreationException("entity type is not defined");

    val ent = global.prototypes(entType)

    entityType = entType

    if (renderer == null) {
      renderer = ent.renderer
    }

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

  def to(newParent: Symbol): Unit = {
    if (newParent == null) {
      // give this object back to its parent before errororing
      if (parent != null){
        parent.objects(name) = this
      }
      moving = false
      throw new IllegalArgumentException("instance is null")
    }

    if (newParent == name){
      if (parent != null){
        parent.objects(name) = this
      }
      moving = false
      throw new IllegalArgumentException("cannot give entity to itself")
    }

    if (!(global.entities contains newParent) && !(global.scenes contains newParent)){
      if (parent != null){
        parent.objects(name) = this
      }
      moving = false
      throw new UndefinedInstanceException("instance is null")
    }

    if (!moving) {
      if (parent != null){
        parent.objects(name) = this
      }
      throw new IllegalStateException("to without gives")
    }

    if(global.entities contains newParent){
      global.entities(newParent).objects(name) = this
    } else {
      global.scenes(newParent).objects(name) = this
    }

    // getting from nobody
    if(!(nobody isUsed name)){
      nobody use name
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
