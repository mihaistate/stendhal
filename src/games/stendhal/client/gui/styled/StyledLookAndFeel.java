package games.stendhal.client.gui.styled;

import javax.swing.UIDefaults;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class StyledLookAndFeel extends MetalLookAndFeel {
	private static final String pkg = "games.stendhal.client.gui.styled.";
	
	private final Style style;
	
	public StyledLookAndFeel(Style style) {
		super();
		this.style = style;
	}
	
	@Override
	protected void initClassDefaults(UIDefaults table) {
		super.initClassDefaults(table);

		Object[] uiDefaults = {
			// Provide access to the style for the components
			"StendhalStyle", style,
			// The component UIs
			"ButtonUI", pkg + "StyledButtonUI",
			"CheckBoxUI", pkg + "StyledCheckBoxUI",
			"ComboBoxUI", pkg + "StyledComboBoxUI",
			"LabelUI", pkg + "StyledLabelUI",
			"MenuItemUI", pkg + "StyledMenuItemUI",
			"OptionPaneUI", pkg + "StyledOptionPaneUI",
			"PanelUI", pkg + "StyledPanelUI",
			"PasswordFieldUI", pkg + "StyledPasswordFieldUI",
			"PopupMenuUI", pkg + "StyledPopupMenuUI",
			"ProgressBarUI", pkg + "StyledProgressBarUI",
			"ScrollBarUI", pkg + "StyledScrollBarUI",
			"ScrollPaneUI", pkg + "StyledScrollPaneUI",
			"SliderUI", pkg + "StyledSliderUI",
			"SplitPaneUI", pkg + "StyledSplitPaneUI",
			"TextFieldUI", pkg + "StyledTextFieldUI",
			"ToolTipUI", pkg + "StyledToolTipUI",
		};
		
		table.putDefaults(uiDefaults);
	}
	
	@Override
	public boolean isSupportedLookAndFeel() {
		// supported everywhere
		return true;
	}
	
	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return "Stendhal pixmap look and feel";
	}
	
	@Override
	public String getID() {
		return "Stendhal";
	}
	
	@Override
	public String getName() {
		return "Stendhal";
	}
}
