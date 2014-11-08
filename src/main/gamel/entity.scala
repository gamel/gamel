package gamel;

import scala.collection.mutable.{Map, HashMap}

// represents an entity-type
protected[gamel] case class Entity(t: Symbol) extends Thing {
    val entity_type = t
    var ownedEntities = new HashMap[Symbol, Property]
    def as(inner: Property*) = {
        println("Giving properties to entity-type " + entity_type)
        // do the validation here
        // if the property name has already been defined in this entity
        // then we know it is bad
        inner foreach { property => 
          if (ownedEntities contains property.name) {
            throw new InstantiationException(property.name + " already exists")
          }
          // adding into the property map
          ownedEntities(property.name) = property
        }
         
      }
}

protected[gamel] case class EntityInstance(t: Entity) extends Thing {
    val entity_type = t
    var entity_name: Symbol = null
    var ownedEntities: HashMap[Symbol, EntityInstance] = new HashMap[Symbol, EntityInstance]

    /**
     * called(...) instantiate the entity and its own entities
     * @param name: Symbol = name of the instance we try to instantiate
     * @param isGlobal: Boolean = indicator of the entity scope
     * */
    def called(name: Symbol, isGlobal: Boolean = true): Unit = {
        // we are using called(...) to recursively create objects
        // therefore we have to make sure that the method know
        // whether this object is global or local
        if (isGlobal) {
          if(all contains name){
            throw new InstantiationException(name + " already exists")
          }
          // put this object into global scope 
          all(name) = this
          println("entity instance called " + name)
        }
        
        entity_name = name

        // instantiate all owned entities RECURSIVELY!!!
        t.ownedEntities.foreach {
          keyVal => {
            // keyVal._1 is the name of the property
            // keyVal._2 is the property
            keyVal._2 match {
              // this corresponds to "has - ..." syntax
              case h: HasObj => { 
                val ent = all(h.entity_type).asInstanceOf[Entity]
                var instance = (new EntityInstance(ent))

                instance called(keyVal._1, false) 

                ownedEntities(keyVal._1) = instance
              }
              case a: ActionObj => {
                throw new UnsupportedOperationException("Action Object has not been implemented yet! What's wrong with you?")
              }

            } // end of match

          } 

        } // end of iteration on owned entities

    }
}
