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

- In eclipse, create new project "Icon-match" and "AndroidGameEngine" as "General Project".
 - The eclipse will idenify it is Android project by itself.
 - Icon-match should have error, dont worry.
 
- Edit build.mk, change the ANDROIDKIT_PATH to the android_kit path.  It should contains the file ods2xml.sh.

- In shell
 - cd Icon-match
 - ./build_support_script

- Refresh IconMatch project, it should be ready to compile.
