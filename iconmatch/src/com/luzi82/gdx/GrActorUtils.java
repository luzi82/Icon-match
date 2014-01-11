package com.luzi82.gdx;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;

public class GrActorUtils {

	public static void setBound(Actor aActor, Rectangle aRect) {
		aActor.setBounds(aRect.x, aRect.y, aRect.width, aRect.height);
	}

	public static <T> Listener<T> connectValue(final Label aLabel, final Value<T> aValue) {
		Listener<T> listener = new Listener<T>() {
			@Override
			public void onValueDirty(Value<T> v) {
//				System.err.println("onValueDirty");
				aLabel.addAction(new Action() {
					@Override
					public boolean act(float delta) {
//						System.err.println("act");
						aLabel.setText(aValue.get().toString());
						return true;
					}
				});
			}
		};
		aValue.addListener(listener);
		aLabel.setText(aValue.get().toString());
		return listener;
	}

}
