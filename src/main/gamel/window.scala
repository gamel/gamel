package gamel

import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.GraphicsConfiguration
import javax.swing.JFrame
import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import scala.collection.mutable.{Map, HashMap}
import javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE

/**
 * IDEA GAMEL tries to integrate GUI with it.
 * This is the window class that prepare such
 * stuff.
 * */
protected[gamel] class GamelWindow(t: String, w: Int, h: Int, fs: Boolean) extends SimpleSwingApplication with Display {

  // basic attributes of a window
  var title: String = t
  var width: Int = w
  var height: Int = h
  var fullscreen: Boolean = fs
  var canvas: GamelCanvas = null
  // screen settings, not supporting fullscreen
  var graphicsEnvironment: GraphicsEnvironment = null
  var graphicsDevice: GraphicsDevice = null
  var graphicsConfiguration: GraphicsConfiguration = null

  /**
   * create a window with canvas
   * */
  def top = new MainFrame {
    // currently does not support fullscreen
    // set up graphics device
    graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
    graphicsDevice = graphicsEnvironment.getDefaultScreenDevice()
    graphicsConfiguration = graphicsDevice.getDefaultConfiguration()

    // basic attributes
    title = this.title
    size = new Dimension(width, height)
    centerOnScreen()
    
    // set icon
    iconImage = toolkit.getImage("res/icon.png")

    // canvas for drawing game components
    canvas = new GamelCanvas {
      preferredSize = new Dimension(width, height)
    }

    // adding canvas into layout
    contents = new BorderPanel {
      layout(canvas) = Center
    }

    peer.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE)

    // window show up in the center
    peer.setLocationRelativeTo(null)
    // enable key press
    canvas.requestFocusInWindow()

    // listeners
    listenTo(canvas.mouse.clicks)
    listenTo(canvas.mouse.wheel)
    listenTo(canvas.mouse.moves)
    listenTo(canvas.keys)

    // hooks
    global.listeners foreach {
      entry => {
        if (entry._2)       // if the listener is enabled
          entry._1 match {
            case "KeyTyped"            =>
              reactions += {
                case KeyTyped(source: Component, char: Char, modifiers: Int, location: Key.Location.Value) =>
                  gamel.keyboard ! (entry._1, source, char, modifiers, location)
              }
            case "KeyPressed"          =>
              reactions += {
                case KeyPressed(source: Component, key: Key.Value, modifiers: Int, location: Key.Location.Value) =>
                  gamel.keyboard ! (entry._1, source, key, modifiers, location)
              }
            case "KeyReleased"         =>
              reactions += {
                case KeyReleased(source: Component, key: Key.Value, modifiers: Int, location: Key.Location.Value) =>
                  gamel.keyboard ! (entry._1, source, key, modifiers, location)
              }
            case "MouseClicked"        =>
              reactions += {
                case MouseClicked(source: Component, point: Point, modifiers: Int, clicks: Int, _) =>
                  gamel.mouse ! (entry._1, source, point, modifiers, clicks)
              }
            case "MouseDragged"        =>
              reactions += {
                case MouseDragged(source: Component, point: java.awt.Point, modifiers: Int) =>
                  gamel.mouse ! (entry._1, source, point, modifiers)
              }
            case "MouseEntered"        =>
              reactions += {
                case MouseEntered(source: Component, point: java.awt.Point, modifiers: Int) =>
                  gamel.mouse ! (entry._1, source, point, modifiers)
              }
            case "MouseMoved"          =>
              reactions += {
                case MouseMoved(source: Component, point: java.awt.Point, modifiers: Int) =>
                  gamel.mouse ! (entry._1, source, point, modifiers)
              }
            case "MousePressed"        =>
              reactions += {
                case MousePressed(source: Component, point: java.awt.Point, modifiers: Int, clicks: Int, _) =>
                  gamel.mouse ! (entry._1, source, point, modifiers, clicks)
              }
            case "MouseReleased"       =>
              reactions += {
                case MouseReleased(source: Component, point: java.awt.Point, modifiers: Int, clicks: Int, _) =>
                  gamel.mouse ! (entry._1, source, point, modifiers, clicks)
              }
            case "MouseWheelMoved"     =>
              reactions += {
                case MouseWheelMoved(source: Component, point: Point, modifiers: Int, rotation: Int) =>
                  gamel.mouse ! (entry._1, source, point, modifiers, rotation)
              }
          }
      }
    }

    // listener for window events
    peer.addComponentListener(new java.awt.event.ComponentListener {
      def componentHidden(e: java.awt.event.ComponentEvent) {
        gamel.gameMsg ! "pause"
      }
      def componentShown(e: java.awt.event.ComponentEvent) {
        gamel.gameMsg ! "resume"
      }
      def componentMoved(e: java.awt.event.ComponentEvent) {
      }
      def componentResized(e: java.awt.event.ComponentEvent) {
        println("I'm resized")
      }
    })

    override def closeOperation() {
      gamel.gameMsg ! "exit"
    }

  } // end of top

  // support for fullscreen
  def isFullScreenSupported = false

  // support for listeners
  def isKeyTypedSupported = true
  def isKeyPressedSupported = true
  def isKeyReleasedSupported = true
  def isMouseClickedSupported = true
  def isMouseDraggedSupported = true
  def isMouseEnteredSupported = true
  def isMouseMovedSupported = true
  def isMousePressedSupported = true
  def isMouseReleasedSupported = true
  def isMouseWheelMovedSupported = true

  // window operation
  def launch(): Unit = {
    this.startup(Array[String]())
  }

  def close(): Unit = {
    gamel.gameMsg ! "exit"
  }

  def repaint(): Unit = {
    canvas.repaint()
  }

}
