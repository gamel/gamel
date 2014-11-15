package idea.gamel

class GamelInstance extends GamelEntity {
  var entityType: Symbol = null
  def of(entType: entity) {
    if(entity == null)
      throw new CreationException("entity type cannot be null");
    if(entity.name == null)
      throw new CreationException("entity type name cannot be null");
    if(!(global.prototypes contains entity.name))
      throw new CreationException("entity type is not defined");

    entityType = entType.name
    attributes = entType.attributes
    renderer = entType.renderer
    actions = entType.actions
    parent = entType.parent
    children = entType.children
  }
}

class instance extends GamelEntity { }
