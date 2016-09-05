ModularArmour
==============

#No Longer Actively Developed

I can no longer work on a code base that is constantly changed, and to be honest, I haven't been able to for a while. 

The problem is that you don't control what users install, and although that provides a lot of power to the end user, it ends up making the Mod Dev's life way to complicated. We spend way to much time working on making our mod work with a constantly changing base that it saps any effort we might put into adding new features. 

Basically every other development source has some version of [NPM's shrinkwrap](https://docs.npmjs.com/cli/shrinkwrap). If you don't want to read it, it basically locks down the versions of any depdencendies you have. Minecraft Modding does not have this, because Minecraft keeps on changing. Depdencencies also keep changing, with my dependency on CoFH Core breaking my mod multiple times. (No Offense, I loved working with you on the small things I added) After a while, it becomes extrmely hard to maintain a large mod, or even a small mod in my case, without spending way too much time changing your mod for every new version somebody else puts out.

Code bases changing is something that every developer has to deal with. Basically every app updates for every new version of iOS and Android. However, the biggest difference is that those systems have deprecation warnings, and a semi-stable form of an API, something which we were promised but never delivered. 

It makes developing a pain. But that's not the only pain. We also have almost no gui framework to work inside of. With iOS, you have Cocoa and UIKit. With websites, you have HTML. All of these are designed to display basically everything at a pretty high level. However, Minecraft has almost nothing like this. There are GUI files, but they are extremely limited. There is no API besides straight LWJGL to work with, and nobody designes GUIs with OpenGL. 

Finally, there is almost no way to automatically download dependencies. Since you can't pack your dependcies with your Mod for fear of conflicts, you have to deal with users installing dependencies manually, something which most devs don't even have to deal with, much less users. 

With constantly changing depdendencies, no frameworks/apis to work with, and a lack of any good way to distribute depdencies to users, we end up writing too much code to do way to little, and spend most of our time trying to maintain said code in a constantly changing landscape.

##TL;DR: So Long, and Thanks for all the Fish

A Minecraft mod that adds Modular Armour.

[![Build Status](http://tehnut.info/jenkins/buildStatus/icon?job=ModularArmour)](http://tehnut.info/jenkins/view/chbachman/job/ModularArmour/)
