Icon Match

======================

Requirement:

Eclipse with Android SDK

android_kit (i18n stuff)
https://github.com/luzi82/android_kit

AndroidGameEngine (Game Engine)
https://github.com/luzi82/AndroidGameEngine

======================

How to compile:

- Download Icon-match source to eclipse workspace.

- Download AndroidGameEngine source to eclipse workspace.

- Download android_kit to anywhere.

- In eclipse, create new project "Icon-match" and "AndroidGameEngine"
 - No need to specify "Android project" in creation.  The eclipse will idenify it when project created.
 - IconMatch should have error, dont worry.
 
- Edit build.mk, change the ANDROIDKIT_PATH to the android_kit path.  It should contains the file ods2xml.sh.

- In command prompt
 - cd IconMatch
 - ./build_support_script

- Refresh IconMatch project, it should be ready to compile.
