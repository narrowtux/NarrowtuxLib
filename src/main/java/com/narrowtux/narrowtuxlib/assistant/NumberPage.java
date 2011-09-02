/*
 * Copyright (C) 2011 Moritz Schmale <narrow.m@gmail.com>
 *
 * NarrowtuxLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

package com.narrowtux.tuxlib.assistant;

public class NumberPage extends AssistantPage {

	private double min = Double.MIN_VALUE, max = Double.MAX_VALUE;
	private Accuracy accuracy;
	private Double value = null;

	public NumberPage(Assistant assistant, Accuracy accuracy) {
		super(assistant);
		this.accuracy = accuracy;
	}

	public void setRange(double min, double max){
		setMinimum(min);
		setMaximum(max);
	}

	public void setMinimum(double min){
		this.min = min;
	}

	public void setMaximum(double max){
		this.max = max;
	}

	@Override
	public AssistantAction onPageInput(String text) {
		double val = 0;
		try{
			val = Double.valueOf(text);
		} catch(Exception e){
			sendMessage("Please enter a valid number");
			return AssistantAction.SILENT_REPEAT;
		}
		if(min<=val&&val<=max){
			value = val;
			return AssistantAction.CONTINUE;
		} else {
			sendMessage("Number out of range!");
			return AssistantAction.SILENT_REPEAT;
		}
	}

	public double getValue(){
		return value;
	}

	public boolean hasValue(){
		return value!=null;
	}
}
