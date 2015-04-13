Chunk Analyzer Mod beta Release 0.72
=======
Note: This is a fork off of Otter In A Suit's original Chunk Analyzer mod. If you are looking for the original project you can find it here: https://github.com/otter-in-a-suit/ChunkAnalyzer 
This version is drastically different from the original project and will continue to diverge.

Content
=======
This mod adds a machine that allows you to analyze the ore make up of a limited area.


Items
=======

Scanners. They all currently act the same.

Config
=======

Debug
-----------
These do what you'd expect them to do. Leave them off unless you have a reason to turn them on.

General
-----------

useXPForScanner = true|false Default - True Consumes experience to scan for ore. (soon to be obsolete)

Reporting
-----------
reportRaw = true|false Default - False Report raw ore amounts detected instead of vague readings like Dense concentration etc. Useful if you want to use this mod with vanilla style ore generation.

Below are reporting thresholds; the values at which or above the scanner will report different concentrations. EG. if there are 80 or more ores in the chunk but less than 90 the scanner will report a Dense concentration of ore. 
These don't do anything if reportRaw is true.

veryDenseThreshold

denseThreshold

minorTheshold

traceThreshold


Disclaimer
=======
This mod is VERY much a work in progress. There will likely be bugs. Please report them here if you find any.


THIS IS A PRE-RELEASE BUILD USE AT YOUR OWN RISK. I am not responsible for corrupted worlds as a result of using this version. 

When using any pre-release builds I highly recommend you delete any configs left over from previous versions and allow a fresh one to generate before loading into a world.

Be aware that item IDs may (Probably will) change causing items from this mod to get deleted from your world when you update to the next version.

Credits:
=======
Otter-In-A-Suit: Original mod

Glockshna: Updating to 1.7.10, bug fixes and new code

Contact
=======
Here @ GitHub or by messaging Glocksna (Yes spelled that way, I typoed when I made my forum account) on the Minecraft Forums

Forums
=======
No post for this fork exists yet the original forum post can be found here:
[Original Chunk Analyzer Mod post by Otter-In-A-Suit](http://bit.ly/1nlBEz3)
