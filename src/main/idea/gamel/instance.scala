package idea.gamel

class GamelInstance extends GamelEntity {
  var entityType: Symbol = null

  def of(entType: Symbol): Unit = {
    if(entType == null)
      throw new CreationException("entity type cannot be null");
    if(!(global.prototypes contains entType))
      throw new CreationException("entity type is not defined");

    val ent = global.prototypes(entType)

    entityType = entType
    attributes = ent.attributes
    renderer = ent.renderer
    actions = ent.actions
    parent = ent.parent
    children = ent.children
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
