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

- Download IconMatch source to eclipse workspace.

- Download android_kit to anywhere.

- Download AndroidGameEngine source to eclipse workspace.

- In eclipse, create new project "IconMatch" and "AndroidGameEngine"
 - No need to specify "Android project" in creation.  The eclipse will idenify it when project created.
 - IconMatch should have error, dont worry.
 
- Open the IconMatch project properties, select "Java Build Path" -> "Source" -> "Link Source".

- In the Link Source window:
 - In Linked folder location, input "[AndroidGameEngine_path]/src".
 - In Folder name, input "game_engine".
 - Use "Ignore nesting conflicts"
 - press "Finish".

- Edit build.mk, change the ANDROIDKIT_PATH to the android_kit path.  It should contains the file ods2xml.sh.

- In command prompt
 - cd IconMatch
 - ./build_support_script

- Refresh IconMatch project, it should be ready to compile.
