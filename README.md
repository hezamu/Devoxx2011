HTML5 with Vaadin & Scala
=========================

This small app demonstrates how Vaadin allows server side Scala programmers to harness the power of HTML5. It was developed for a [presentation](http://www.devoxx.com/display/DV11/Using+Vaadin+to+create+HTML5-enabled+web+apps+in+pure+Scala) given at Devoxx 2011.

The app is written in Scala, and it creates a simple Vaadin UI that contains a HTML5 Canvas animating the Conway's Game of Life. The animation is implemented with the [Refresher add-on](https://vaadin.com/directory/addon/refresher) that periodically pulls the UI state from the server to the browser.

Setup
-----

The project is written in Eclipse with the assistance of Vaadin and Scala IDE plug-ins. In addition to these you need a servlet container, eg. Tomcat, Jetty or similar.
