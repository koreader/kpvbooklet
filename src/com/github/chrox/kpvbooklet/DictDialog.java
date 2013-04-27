package com.github.chrox.kpvbooklet;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import com.amazon.agui.swing.CaretDialog;

public class DictDialog extends CaretDialog {
	
	private static final long serialVersionUID = 1L;
	private static final String APP_ID = "persistant.app";
	
	public DictDialog(String title, JPanel view) {
		super(APP_ID);
		setTitleBarEnabled(true);
		setTitle(title);
		
		JPanel root = (JPanel) getContentPane();
		root.setLayout(new BorderLayout());
		root.add(view, BorderLayout.CENTER);
	}
	
	public static void post(String title, JPanel view) {
		DictDialog d = new DictDialog(title, view);
		d.postDialog(APP_ID, false);
	}
}
