package com.luzi82.iconmatch;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorUtils {

	public static void setBound(Actor aActor, Rectangle aRect) {
		aActor.setBounds(aRect.x, aRect.y, aRect.width, aRect.height);
	}

}
