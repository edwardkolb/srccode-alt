About this Test project
-----

Compiling JSudoku game (http://jsudokukadu.cvs.sourceforge.net/viewvc/jsudokukadu/?view=tar)

Usage
-----

Simply put jsudoku_ant_last.xml file into new jenkins job workspace:

1.Jenkins- New item (Freestyle project) - Build Now
2.Put jsudoku_ant_last.xml file into workspace
3.Configure this job - Add build action (Invoke Ant) - Build File `jsudoku_ant_last.xml`
4.Add Post build action (Publish Checkstyle analysis results) - Checkstyle results `checkstyle_results.xml`
5.Build Now!

Requirements
-----

* Jenkins
* Checkstyle Plugin for Jenkins 3.43
* Ant 