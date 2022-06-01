package view;

import net.miginfocom.swing.MigLayout;

public class LayoutCreator {
	public static MigLayout getFrameLayout() {
		return new MigLayout(
					"debug, align center, wrap",
					"[20%][40%][20%][20%]",
					"[25%][25%][25%][25%]"
				);
	}
	
	public static MigLayout getLTitleLayout() {
		return new MigLayout("align center", "[]", "[grow]");
	}
	
	public static MigLayout getRTitleLayout() {
		return new MigLayout("align center", "[]", "[grow]");
	}

	public static MigLayout getSliderLayout() {
		return new MigLayout("align center", "[grow]", "[grow]");
	}
	
	public static MigLayout getDTextLayout() {
		return new MigLayout("align center", "[]", "[grow]");
	}
}
