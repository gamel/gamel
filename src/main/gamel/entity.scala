package gamel;

// represents an entity-type
class Entity(t: Symbol) {
    def entity_type = t
    def as(inner: List[Property]) = {
        println ("defininging an entity-type with properties")
    }
    def called(n: Symbol) = {
        println ("instantiating an entity with type")
    }
}

