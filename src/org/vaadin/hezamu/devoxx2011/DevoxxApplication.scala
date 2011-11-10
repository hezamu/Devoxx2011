package org.vaadin.hezamu.devoxx2011

import org.vaadin.hezamu.canvas.Canvas
import com.vaadin.Application
import com.github.wolfie.refresher._

import vaadin.scala._

/**
 * Demo application for a Devoxx 2011 talk about using Vaadin and Scala for
 * HTML5 trickery. It animates the Conway's Game of Life using Canvas.
 *
 * @author Henri Muurimaa, henri@vaadin.com
 */
class DevoxxApplication extends Application {
	// Create and initialize the server-side proxy for the canvas
	val canvas = new Canvas()
	canvas.setSizeFull
	canvas.setBackgroundColor("#000")
	canvas.setFillStyle("#fff")

	val world = new World // Game of Life board

	// Create and initialize the refresher, which will periodically pull
	// the UI state to the brow
	val refresher = new Refresher()
	refresher.setRefreshInterval(100)
	refresher.addListener(new Refresher.RefreshListener() {
		def refresh(source: Refresher): Unit = {
			canvas.reset

			world.increment

			world.cells.foreach(drawCell)
		}
	})

	// Vaadin UI initializer
	def init {
		// Create & setup the main window
		setMainWindow(new Window("Hello Devoxx", content = new VerticalLayout(height = 100 percent, margin = false) {
			add(canvas, 1)
			add(refresher)
		}))

		world.cells.foreach(drawCell) // Draw the initial state of the world.
	}

	/**
	 * Draw a rect corresponding to a cell. Fill style is changed if necessary.
	 */
	def drawCell(cell: (Int, Int)) {
		canvas.fillRect(350 + cell._1 * 8, 150 + cell._2 * 8, 8, 8)
	}
}