package com.narrowtux.Assistant;

public enum AssistantAction {
	/**
	 * cancels the assistant
	 */
	CANCEL,
	/**
	 * finishes the assistant
	 */
	FINISH,
	/**
	 * repeats the current page
	 */
	REPEAT,
	/**
	 * continues with the next page
	 */
	CONTINUE,
	/**
	 * repeat without showing title and text of the page again
	 */
	SILENT_REPEAT,
}
