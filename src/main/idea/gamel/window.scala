package idea.gamel

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import scala.collection.mutable.{Map, HashMap}

/**
 * IDEA GAMEL tries to integrate GUI with it.
 * This is the window class that prepare such 
 * stuff.
 * */
class GamelWindow(t: String, w: Int, h: Int) extends SimpleSwingApplication {

  // basic attributes of a window
  var title: String = t
  var width: Int = w
  var height: Int = h
  var menubar: Boolean = true
  var canvas: GamelCanvas = null

  /**
   * create a window with canvas
   * */
  def top = new MainFrame {
    // basic attributes
    title = this.title
    size = new Dimension(width, height)

    // using menubar?
    if (menubar) {
      menuBar = new MenuBar {
        // adding new menu
        contents += new Menu("Menu") {
          // adding new menu item "Exit"
          contents += new MenuItem(Action("Exit") {
            sys.exit(0)
          })
        }
      }
    }

    // canvas for drawing game components
    canvas = new GamelCanvas {
      preferredSize = new Dimension(width, height)
    }

    // adding canvas into layout
    contents = new BorderPanel {
      layout(canvas) = Center
    }

    // window show up in the center
    peer.setLocationRelativeTo(null)

    // listeners
    listenTo(canvas.mouse.clicks)
    // listenTo(canvas.keys)

    // hooks
    reactions += {
      // case UIElementResized(elem)   => println("I was resized!" + elem)    // resizez
      case ButtonClicked(component) => println("button clicked!")
      case MouseClicked(_, point, _, _, _) => println("mouse clicked at " + point)
      // case KeyPressed(_, key, _, _) => println("key press at " + key)
    }

    // periodic repainting
    canvas.start()
  } // end of top

}
