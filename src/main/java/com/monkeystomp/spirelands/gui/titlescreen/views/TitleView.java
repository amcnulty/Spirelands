package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.monkeystomp.spirelands.gui.interfaces.Viewable;
import com.monkeystomp.spirelands.view.LevelView;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class TitleView implements Viewable {
  
  protected Consumer<LevelView> ILevelViewSetter;
  protected Consumer<TitleView> ITitleViewSetter;
  protected Consumer<Float> IVolumeSetter;
  
  public TitleView(Consumer<LevelView> ILevelViewSetter, Consumer<TitleView> ITitleViewSetter, Consumer<Float> IVolumeSetter) {
    this.ILevelViewSetter = ILevelViewSetter;
    this.ITitleViewSetter = ITitleViewSetter;
    this.IVolumeSetter = IVolumeSetter;
  }

}
