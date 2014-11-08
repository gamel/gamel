package gamel;

// represents an entity-type
protected[gamel] case class Entity(t: Symbol) extends Thing {
    val entity_type = t
    var ownedEntities: List[Property] = List()
    def as(inner: Property*) = {
        println("Giving properties to entity-type " + entity_type)
        ownedEntities = List(inner:_*)
    }
}

protected[gamel] case class EntityInstance(t: Entity) extends Thing {
    val entity_type = t
    var entity_name = 'Placeholder
    def called(name: Symbol) = {
        if(all contains name){
            throw new InstantiationException(name + " already exists")
        }
        println("entity instance called " + name)
        entity_name = name
        all(name) = this
    }
}
