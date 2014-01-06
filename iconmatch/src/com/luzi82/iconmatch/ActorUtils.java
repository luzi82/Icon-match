package com.luzi82.iconmatch;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;

public class ActorUtils {

	public static void setBound(Actor aActor, Rectangle aRect) {
		aActor.setBounds(aRect.x, aRect.y, aRect.width, aRect.height);
	}

	public static <T> void connectValue(final Label aLabel, final Value<T> aValue) {
		aValue.addListener(new Listener<T>() {
			@Override
			public void onValueDirty(Value<T> v) {
				aLabel.addAction(new Action() {
					@Override
					public boolean act(float delta) {
						aLabel.setText(aValue.get().toString());
						return true;
					}
				});
			}
		});
		aLabel.setText(aValue.get().toString());
	}

}
