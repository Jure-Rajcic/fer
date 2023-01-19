package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretListener;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CopyAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CutAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ExitAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.InvertCaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.NewDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.PasteAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveAsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortAscendingAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortDescendingAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.StatisticsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ToLowerCaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ToUpperCaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.UniqueAction;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.DefaultCaretListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.DefaultMultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listeners.DefaultSingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.Clock;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentListener;
import java.awt.event.ActionEvent;
/**
 * class JNotepadPP represents GUI that user uses
 * 
 * @author Jure Rajcic
 *
 */
public class JNotepadPP extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setSize(600,600);
		initGUI();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JNotepadPP.this.exitAction.actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, null));
			}
		});
	}
	
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	public FormLocalizationProvider getFormLocalizationProvider() { return this.flp; }

	private DefaultMultipleDocumentModel model = new DefaultMultipleDocumentModel();
	public DefaultMultipleDocumentModel getDefaultMultipleDocumentModel() { return this.model; }

	private String copyPaste;
	public String getCopyPaste() { return this.copyPaste; }

	private JPanel statusBar;
	public JPanel getStatusBar() { return this.statusBar; }

	private Clock clock;
	public Clock getClock() { return this.clock; }
	
	private CaretListener cl = new DefaultCaretListener(this);
	public CaretListener getCaretListener() { return this.cl; }

	private SingleDocumentListener sdl = new DefaultSingleDocumentListener(this);
	public SingleDocumentListener getSingleDocumentListener() { return sdl; }
	
	private LocalizableAction openDocumentAction = new OpenDocumentAction(this);
	public LocalizableAction getOpenDocumentAction() { return this.openDocumentAction; }

	private LocalizableAction newDocumentAction = new NewDocumentAction(this);
	public LocalizableAction getNewDocumentAction() { return this.newDocumentAction; }

	private LocalizableAction saveAction = new SaveAction(this);
	public LocalizableAction getSaveAction() { return this.saveAction; }

	private LocalizableAction saveAsAction = new SaveAsAction(this);
	public LocalizableAction getSaveAsAction() { return this.saveAsAction; }
	
	private LocalizableAction closeDocumentAction = new CloseDocumentAction(this);
	public LocalizableAction getCloseDocumentAction() { return this.closeDocumentAction; }

	private LocalizableAction exitAction = new ExitAction(this);
	public LocalizableAction getExitAction() { return this.exitAction; }

	private LocalizableAction statisticsAction = new StatisticsAction(this);
	public LocalizableAction getStatisticsAction() { return this.statisticsAction; }

	private LocalizableAction copyAction = new CopyAction(this);
	public LocalizableAction getCopyAction() { return this.copyAction; }
	public void setCopyPaste(String copyPaste) { this.copyPaste = copyPaste; }

	private LocalizableAction cutAction = new CutAction(this);
	public LocalizableAction getCutAction() { return this.cutAction; }
	
	
	private LocalizableAction pasteAction = new PasteAction(this);
	public LocalizableAction getPasteAction() { return this.pasteAction; }

	private LocalizableAction toLowerCaseAction = new ToLowerCaseAction(this);
	public LocalizableAction getToLowerCaseAction() { return this.toLowerCaseAction; }

	private LocalizableAction toUpperCaseAction = new ToUpperCaseAction(this);
	public LocalizableAction getToUpperCaseAction() { return this.toUpperCaseAction; }

	private LocalizableAction invertCaseAction = new InvertCaseAction(this);
	public LocalizableAction getInvertCaseAction() { return this.invertCaseAction; }

	private LocalizableAction ascendingSort = new SortAscendingAction(this);
	public LocalizableAction getSortAscendingAction() { return this.ascendingSort; }

	private LocalizableAction descendingSort = new SortDescendingAction(this);
	public LocalizableAction getSortDescendingAction() { return this.descendingSort; }

	private LocalizableAction unique = new UniqueAction(this);
	public LocalizableAction getUniqueAction() { return this.unique; }


	private void initGUI() {
		
		model.addMultipleDocumentListener(new DefaultMultipleDocumentListener(this));
		// GUI for status bar
		{
			this.statusBar = new JPanel();
			this.statusBar.setLayout(new GridLayout(0,3));
			this.statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
			
			JLabel length = new JLabel("Length:0");
			length.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			this.statusBar.add(length);
			
			JLabel lnColSel = new JLabel("Ln:0 Col:0 Sel:0");
			lnColSel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			this.statusBar.add(lnColSel);
			
			this.clock = new Clock();
			this.statusBar.add(clock);
		}
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(model), BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		GUIMenu();
		GUIToolbars();
	}

	private void GUIMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		{
			fileMenu.add(new JMenuItem(newDocumentAction));
			fileMenu.add(new JMenuItem(openDocumentAction));
			fileMenu.add(new JMenuItem(saveAction));
			fileMenu.add(new JMenuItem(saveAsAction));
			fileMenu.add(new JMenuItem(closeDocumentAction));
			fileMenu.addSeparator();
			fileMenu.add(new JMenuItem(exitAction));
		}
		menuBar.add(fileMenu);

		
		JMenu editMenu = new JMenu("Edit");
		{
			editMenu.add(new JMenuItem(statisticsAction));
			editMenu.add(new JMenuItem(copyAction));
			editMenu.add(new JMenuItem(cutAction));
			editMenu.add(new JMenuItem(pasteAction));
		}
		menuBar.add(editMenu);

		
		JMenu toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);
		
		JMenu changeCaseMenu =  new JMenu("Change case");
		{
			changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
			changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
			changeCaseMenu.add(new JMenuItem(invertCaseAction));
		}
		toolsMenu.add(changeCaseMenu);
		
		JMenu sortMenu = new JMenu("Sort");
		{
			sortMenu.add(new JMenuItem(ascendingSort));
			sortMenu.add(new JMenuItem(descendingSort));
		}

		toolsMenu.add(sortMenu);
		toolsMenu.add(new JMenuItem(unique));
		
		ActionListener languageListener = e -> {
			JMenuItem item = (JMenuItem) e.getSource();
			LocalizationProvider.getInstance().setLanguage(item.getText());
			flp.fire();
		};
		
		JMenu languageMenu = new JMenu("Languages");
		{
			JMenuItem en = new JMenuItem("en");
			en.addActionListener(languageListener);
			languageMenu.add(en);
	
			JMenuItem hr = new JMenuItem("hr");
			hr.addActionListener(languageListener);
			languageMenu.add(hr);

			JMenuItem de = new JMenuItem("de");
			de.addActionListener(languageListener);
			languageMenu.add(de);
		}
		
		menuBar.add(languageMenu);
		this.setJMenuBar(menuBar);
		
		flp.addLocalizationListener(() -> {
			languageMenu.setText(flp.getString("languages"));
			toolsMenu.setText(flp.getString("tools"));
			editMenu.setText(flp.getString("edit"));
			fileMenu.setText(flp.getString("file"));
			sortMenu.setText(flp.getString("sort"));
			changeCaseMenu.setText(flp.getString("changeCase"));
		});
	}
	
	
	private void GUIToolbars() {
		JToolBar toolbar = new JToolBar("Tools");
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton(newDocumentAction));
		toolbar.add(new JButton(openDocumentAction));
		toolbar.add(new JButton(saveAction));
		toolbar.add(new JButton(saveAsAction));
		toolbar.add(new JButton(closeDocumentAction));
		
		toolbar.addSeparator();
		
		toolbar.add(new JButton(exitAction));
		
		toolbar.addSeparator();
		
		toolbar.add(new JButton(statisticsAction));
		toolbar.add(new JButton(copyAction));
		toolbar.add(new JButton(cutAction));
		toolbar.add(new JButton(pasteAction));
		
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}
	
	
	public static void main(String[] args) {
		// TODO pogledati shorcute ponovo, mislim da imam bug
		SwingUtilities.invokeLater(() -> {
			LocalizationProvider.getInstance().setLanguage("en");
			new JNotepadPP().setVisible(true);
		});
	}
}
