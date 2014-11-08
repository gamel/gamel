package gamel;

// represents an entity-type
protected[gamel] class Entity(t: Symbol) {
    def entity_type = t
    def as(inner: Property*) = {
        println ("defininging an entity-type with properties")
    }
    def called(n: Symbol) = {
        println ("instantiating an entity with type")
    }
}

