package fv.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dsc.util.function.UUID;

public abstract class SimpleExcelTask extends SimpleTask {
	private HSSFWorkbook workBook;

	public SimpleExcelTask(File templateFile) {
		try {
			loadTemplate(templateFile);
		} catch (IOException e) {
			workBook = new HSSFWorkbook();
			e.printStackTrace();
		}
	}

	public void loadTemplate(File templateFile) throws IOException {
		FileInputStream in = new FileInputStream(templateFile);
		workBook = new HSSFWorkbook(in);
	}

	@Override
	public boolean execute() {
		if (workBook != null) {
			return fillData();
		}
		return false;
	}

	protected abstract boolean fillData();

	@Override
	public File save(String fileName) throws IOException {
		File f = new File(System.getProperty("java.io.tmpdir"), UUID.generate());
		f.deleteOnExit();
		FileOutputStream out = new FileOutputStream(f);
		workBook.write(out);
		out.flush();
		out.close();
		return f;
	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

}
