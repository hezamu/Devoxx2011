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

	val world = new World // Game of Life board

	// Cell colors: empty cell, just died, stays alive, just became alive
	val fillStyles = List("#000", "#311", "#ddd", "#afa")
	var currentStyle = ""

	// Create and initialize the refresher, which will periodically pull
	// the UI state to the brow
	val refresher = new Refresher()
	refresher.setRefreshInterval(100)

	// Vaadin UI initializer
	def init {
		// Create & setup the main window
		setMainWindow(new Window("Hello Devoxx", content = new VerticalLayout(height = 100 percent, margin = false) {
			add(canvas, 1)
			add(refresher)
		}))

		// Draw the initial state of the world. The grid is sorted by cell
		// state before drawing to minimize fill style changes
		world.cells.toSeq.sortBy(_._2).foreach(drawCell)

		refresher.addListener(new Refresher.RefreshListener() {
			def refresh(source: Refresher): Unit = {
				canvas.reset

				world.increment

				world.cells.toSeq.sortBy(_._2).foreach(drawCell)
			}
		})
	}

	/**
	 * Draw a rect corresponding to a cell. Fill style is changed if necessary.
	 */
	def drawCell(cell: ((Int, Int), Int)) {
		if (currentStyle != fillStyles(cell._2)) {
			currentStyle = fillStyles(cell._2)
			canvas.setFillStyle(currentStyle)
		}

		canvas.fillRect(350 + cell._1._1 * 8, 150 + cell._1._2 * 8, 8, 8)
	}
}