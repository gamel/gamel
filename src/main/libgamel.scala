//import scala.collection.mutable.{Map, HasObjhMap}
//import scala.language.implicitConversions

sealed trait Property

case class HasObj(t: Symbol) extends Property{
    var entity_type = t
    var entity_name = 'Placeholder
    def > (n: Symbol): HasObj = {
        this.entity_name = n
        this
    }
}

class Entity(t: Symbol) {
    def entity_type = t
    def as(inner: List[Property]) = {
        println ("creating a entity with properties")
    }
}

object define {
    def entity (thing: Entity): Entity = {
        println ("creating a thing")
        thing
    }
}

object has {
    def - (s: Symbol): HasObj = new HasObj(s)
}

object gamel {
    implicit def symbolToEntity(s: Symbol): Entity = new Entity(s)
    def <(owned: Property*): List[Property] = List(owned:_*)
}
