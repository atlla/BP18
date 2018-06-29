package helpercomponents;

import java.awt.Font;

import javafx.scene.control.Tooltip;

public final class TooltipFactory {
	
	private TooltipFactory(){
		
	}
	
	public static Tooltip createTooltip(String text){
		
		final Tooltip tooltip = new Tooltip();
		tooltip.setText(text);
		return tooltip;
	}
	
	public static Tooltip createTooltip(String text, boolean wrapText){
		
		final Tooltip tooltip = new Tooltip();
		tooltip.setMaxWidth(200);
		tooltip.setStyle("-fx-background-color: white; -fx-text-fill: black;");
		
		tooltip.setText(text);
		tooltip.setWrapText(wrapText);
		return tooltip;
	}
}
