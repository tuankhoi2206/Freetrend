package ds.program.fvhr.minh;

import java.util.EventListener;
import java.util.ResourceBundle;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.layout.SplitPaneLayoutData;
import nextapp.echo2.app.Insets;

/**
 * A generic modal dialog that displays a message.
 */
public class MessageDialog extends WindowPane {
    
    /**
     * Command provided in <code>ActionEvent</code>s when the user presses the 
     * 'cancel' or 'no' button.
     */
    public static final String COMMAND_CANCEL = "cancel";
    
    /**
     * Command provided in <code>ActionEvent</code>s when the user presses the 
     * 'ok' or 'yes' button.
     */
    public static final String COMMAND_OK = "ok";
    
    /**
     * Control configuration constant indicating that only an 'ok' button should
     * be displayed.
     */
    public static final int CONTROLS_OK = 1;
    
    /**
     * Control configuration constant indicating that only an 'yes' and 'no' 
     * buttons should be displayed.
     */
    public static final int CONTROLS_YES_NO = 2;
    
    private ActionListener actionProcessor = new ActionListener() {

        /**
         * @see nextapp.echo2.app.event.ActionListener#actionPerformed(nextapp.echo2.app.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            getParent().remove(MessageDialog.this);
            EventListener[] listeners = getEventListenerList().getListeners(ActionListener.class);
            ActionEvent outgoingEvent = new ActionEvent(this, e.getActionCommand());
            for (int i = 0; i < listeners.length; ++i) {
                ((ActionListener) listeners[i]).actionPerformed(outgoingEvent);
            }
        }
    };

    private Label contentLabel;
    private int controlConfiguration;
    private Row controlsRow;
    private ResourceBundle resourceBundle;

    /**
     * Creates a new <code>MessageDialog</code>.
     */
    public MessageDialog() {
        super();

        // Add design-time configured components.
        initComponents();
    }
    
    /**
     * Creates a new <code>MessageDialog</code>.
     * 
     * @param title the dialog title
     * @param message the message to display
     * @param controlConfiguration the control configuration, one of the 
     *        following values:
     *        <ul>
     *         <li><code>CONTROLS_OK</code></li>
     *         <li><code>CONTROLS_YES_NO</code></li>
     *        </ul>
     */
    public MessageDialog(String title, String message, int controlConfiguration) {
        this();
        setTitle(title);
        setMessage(message);
        setControlConfiguration(controlConfiguration);
    }
    
    /**
     * Adds an <code>ActionListener</code> to receive notification when the
     * user selects a choice.  The fired <code>command</code> of the fired 
     * <code>ActionEvent</code> will contain be one of the 
     * <code>COMMAND_XXX</code> constants.
     * 
     * @param l the <code>ActionListener</code> to add
     */
    public void addActionListener(ActionListener l) {
        getEventListenerList().addListener(ActionListener.class, l);
    }
    
    /**
     * Returns the control configuration.
     * 
     * @return the control configuration, one of the following values:
     *         <ul>
     *          <li><code>CONTROLS_OK</code></li>
     *          <li><code>CONTROLS_YES_NO</code></li>
     *         </ul>
     */
    public int getControlConfiguration() {
        return controlConfiguration;
    }
    
    /**
     * Returns the displayed message.
     * 
     * @return the displayed message
     */
    public String getMessage() {
        return contentLabel.getText();
    }
    
    /**
     * Removes an <code>ActionListener</code> from receiving notification 
     * when the user selects a choice.
     * 
     * @param l the <code>ActionListener</code> to remove
     */
    public void removeActionListener(ActionListener l) {
        getEventListenerList().removeListener(ActionListener.class, l);
    }
    
    /**
     * Sets the control configuration.
     * 
     * @param newValue the new configuration, one of the following values:
     *        <ul>
     *         <li><code>CONTROLS_OK</code></li>
     *         <li><code>CONTROLS_YES_NO</code></li>
     *        </ul>
     */
    public void setControlConfiguration(int newValue) {
        controlConfiguration = newValue;
        controlsRow.removeAll();
        Button button;
        switch (controlConfiguration) {
        case CONTROLS_OK:
            button = new Button(resourceBundle.getString("Generic.Ok"), Styles.ICON_24_YES);
            button.setStyleName("ControlPane.Button");
            button.setActionCommand(COMMAND_OK);
            button.addActionListener(actionProcessor);
            controlsRow.add(button);
            break;
        case CONTROLS_YES_NO:
            button = new Button(resourceBundle.getString("Generic.Yes"), Styles.ICON_24_YES);
            button.setStyleName("ControlPane.Button");
            button.setActionCommand(COMMAND_OK);
            button.addActionListener(actionProcessor);
            controlsRow.add(button);
            button = new Button(resourceBundle.getString("Generic.No"), Styles.ICON_24_NO);
            button.setStyleName("ControlPane.Button");
            button.setActionCommand(COMMAND_CANCEL);
            button.addActionListener(actionProcessor);
            controlsRow.add(button);
            break;
        }
    }

    /**
     * Sets the displayed message.
     * 
     * @param message the displayed message text.
     */
    public void setMessage(String message) {
        contentLabel.setText(message);
    }

    /**
     * Configures initial state of component.
     * WARNING: AUTO-GENERATED METHOD.
     * Contents will be overwritten.
     */
    private void initComponents() {
        resourceBundle = ResourceBundle.getBundle(
                "dsc.echo2app.resource.localization.CommonMsg_vi",
                ApplicationInstance.getActive().getLocale());
        this.setStyleName("Default");
        this.setModal(true);
        SplitPane splitPane1 = new SplitPane();
        splitPane1.setStyleName("ControlPane.Container.Bottom");
        add(splitPane1);
        controlsRow = new Row();
        controlsRow.setStyleName("ControlPane");
        splitPane1.add(controlsRow);
        contentLabel = new Label();
        contentLabel.setStyleName("MessageDialog.ContentLabel");
        SplitPaneLayoutData layoutData1 = new SplitPaneLayoutData();
        layoutData1.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(
                5, Extent.PX)));
        contentLabel.setLayoutData(layoutData1);
        splitPane1.add(contentLabel);
    }
}
