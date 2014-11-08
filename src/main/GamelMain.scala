import gamel._

object GamelCode extends App {

    define entity 'Tianyu as <(
        has - 'Foo > 'foo,
        has - 'Bar > 'bar
        
    ) 

    create scene 'Room as <(
        has - 'Fizz > 'buzz
    )

    create entity 'Tianyu called 'tianyu

}
