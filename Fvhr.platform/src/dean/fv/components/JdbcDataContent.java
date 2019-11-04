package fv.components;

import nextapp.echo2.app.ContentPane;

public abstract class JdbcDataContent extends ContentPane {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new <code>JdbcDataContent</code>.
	 */
	public JdbcDataContent() {
		super();

		// Add design-time configured components.
		initComponents();
	}
	
	public abstract boolean refetch();

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}
}
