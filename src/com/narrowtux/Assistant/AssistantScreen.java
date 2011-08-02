package com.narrowtux.Assistant;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.gui.Widget;

public class AssistantScreen extends GenericWindow {
	private Assistant assistant;
	private Label pageText = null;
	private TextField pageInput = null;
	private Button button = null;
	private MessageBox currentMsg = null;
	public AssistantScreen(Assistant assistant) {
		super(assistant.getTitle(), assistant.getSpoutPlayer());
		this.assistant = assistant;
	}
	
	public void renderPage(AssistantPage page){
		//Labels
		pageText = updateLabel(pageText, ChatColor.WHITE+page.getText(), getMarginLeft(), getMarginTop()+40,  getWidth()-20, 10);
		//Input field
		if(pageInput==null)
		{
			pageInput = new GenericTextField();
			pageInput.setX(getMarginLeft()).setY(getMarginTop()+60).setWidth(getWidth()-20).setHeight(20);
			pageInput.setMaximumCharacters(100);
			attachWidget(pageInput);
		}
		pageInput.setText("");
		pageInput.setDirty(true);
		//OK Button
		if(button==null)
		{
			button = new GenericButton("Send");
			button.setX(getMarginLeft()).setY(getMarginBottom()-10).setWidth(200).setHeight(20);
			attachWidget(button);
		}
	}
	
	private Label createLabel(String text, int x, int y, int width, int height){
		Label lbl = new GenericLabel(text);
		lbl.setX(x).setY(y).setWidth(width).setHeight(height);
		attachWidget(lbl);
		return lbl;
	}
	
	private Label updateLabel(Label lbl, String text, int x, int y, int width, int height){
		if(lbl == null){
			lbl = createLabel(text, x, y, width, height);
		} else {
			lbl.setX(x).setY(y).setWidth(width).setHeight(height);
			lbl.setText(text);
			lbl.setDirty(true);
		}
		return lbl;
	}

	public Assistant getAssistant() {
		return assistant;
	}

	public void handleClick(Button btn) {
		if(btn.equals(button)){
			Assistant.onPlayerChat(new PlayerChatEvent(getAssistant().getPlayer(), pageInput.getText()));
		} 
		if(currentMsg!=null&&btn.equals(currentMsg.getOkButton())){
			detachMessageBox(currentMsg);
		}
	}

	public void attachMessageBox(MessageBox msg) {
		currentMsg = msg;
		for(Widget w:msg.getAttachedWidgets()){
			attachWidget(w);
		}
	}
	
	public void detachMessageBox(MessageBox msg) {
		for(Widget w:msg.getAttachedWidgets()){
			w.setVisible(false);
			w.setDirty(true);
			//removeWidget(w);
		}
		currentMsg = null;
	}
	
	public Button getNextButton(){
		return button;
	}
}
