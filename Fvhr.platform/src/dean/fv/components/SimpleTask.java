package fv.components;

import java.io.File;
import java.io.IOException;

public abstract class SimpleTask {
	public abstract boolean execute();

	public abstract File save(String fileName) throws IOException;
}
