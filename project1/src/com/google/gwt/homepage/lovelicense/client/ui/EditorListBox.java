package com.google.gwt.homepage.lovelicense.client.ui;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.ListBox;

public class EditorListBox  extends ListBox implements
LeafValueEditor<String> {

	@Override
	public void setValue(String value) {
		if (value == null) {
		  setSelectedIndex(0);//첫번째것을 선택하도록
		  return;
		}

		for (int i=0;i<getItemCount();i++) {
			if (getValue(i).equals(value)) {
				setSelectedIndex(i);
				return;
			}
		}

		for (int i=0;i<getItemCount();i++) {
			if (getItemText(i).equals(value)) {
				setSelectedIndex(i);
				return;
			}
		}

	}

	@Override
	public String getValue() {
		if (getSelectedIndex() == -1)//선택되지 않았으면
			return null;

		if (getItemText(getSelectedIndex()).trim().equals(""))
			return null;

		String value = getValue(getSelectedIndex());

		if (value != null) {
			return value;
		}

		return getItemText(getSelectedIndex());
	}

}

