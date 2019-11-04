package ds.program.fvhr.ngan.ui.advance;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ds.program.fvhr.domain.N_EMPLOYEE;
import ds.program.fvhr.son.ui.ObjUtility;
import ds.program.fvhr.util.OBJ_UTILITY;
import dsc.dao.IGenericDAO;
import dsc.echo2app.Application;
import fv.util.Vni2Uni;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.TextField;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.button.ButtonGroup;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

public class Advance_WPane extends WindowPane {
	
	private Grid rootlayout;
	private TextField txt_emp;
	private RadioButton r_btn_true;
	private RadioButton r_btn_false;
	private ButtonGroup gr_btn;
	//SimpleDateFormat _sf = new SimpleDateFormat(ObjUtility.DATE_FORMAT_STR);.
	SimpleDateFormat _sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public String _dept;
	
	public Advance_WPane() {
		InitComponent();
	}

	private void InitComponent() {

		rootlayout = new Grid();
		rootlayout.setColumnWidth(0, new Extent(100));
		rootlayout.setRowHeight(0, new Extent(40));
		rootlayout.setRowHeight(1, new Extent(30));
		rootlayout.setRowHeight(2, new Extent(40));
		rootlayout.setRowHeight(3, new Extent(40));
		this.add(rootlayout);
		
		
		
		
		
		rootlayout.add(new Label("Số thẻ"));
		txt_emp = new TextField();
		rootlayout.add(txt_emp);
		
		
		
		gr_btn = new ButtonGroup();
		
		r_btn_false = new RadioButton("Không");
		r_btn_true	= new RadioButton("Có");
		
		r_btn_false.setGroup(gr_btn);
		r_btn_false.setSelected(true);
		r_btn_true.setGroup(gr_btn);
		
		Row radio_btn_row = new Row();
		
		radio_btn_row.setCellSpacing(new Extent(40));
		radio_btn_row.add(r_btn_true);
		radio_btn_row.add(r_btn_false);
		
		
		rootlayout.add(new Label("Ứng lương"));
		rootlayout.add(radio_btn_row);
		
		
		rootlayout.add(new Label());
		
		Row btn_row	= new Row();
		
		Button 	btn_ok = new Button();
				btn_ok.setText("Xác Nhận");
				btn_ok.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
				btn_ok.setStyleName("Default.ToolbarButton");
				btn_ok.setBackground(Color.DARKGRAY);
				btn_ok.setForeground(Color.WHITE);
		
		Button 	btn_export = new Button();
				btn_export.setText("Xuất Dữ Liệu");
				btn_export.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
				btn_export.setStyleName("Default.ToolbarButton");
				btn_export.setBackground(Color.DARKGRAY);
				btn_export.setForeground(Color.WHITE);
		
				
				btn_row.setCellSpacing(new Extent(10));
				btn_row.add(btn_ok);
				btn_row.add(btn_export);
		
		rootlayout.add(btn_row);
		
		
		
		btn_ok.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {

				if(txt_emp.getText() == null || txt_emp.getText().trim().equals("") || txt_emp.getText().length() != 8){
					
					OBJ_UTILITY.ShowMessageError("Nhập số thẻ cần xác nhận");
				}else{
				
					action_btn_ok();
				}
			}
		});
		
		btn_export.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {

				action_btn_export();
			}
		});
		
		
		/*// test Dept_UserControl
		
		Dept_UserControl 	my_select_dept = new Dept_UserControl();
		rootlayout.add(my_select_dept);*/
		
		
	}

	
	
	protected void action_btn_export() {
		boolean checked = r_btn_true.isSelected();
		ExportAdvance_WPane export_win = new ExportAdvance_WPane(checked);
		Application.getApp().getDefaultWindow().getContent().add(export_win);
	}

	protected void action_btn_ok() {
		
			// update status advance
		int status 		= 0;
		String empsn 	= "";
		Boolean rs		= true;
		
		String 	user_up = Application.getApp().getLoginInfo().getUserID();
	//	Date	date = null;
		java.sql.Timestamp date = null;
		String date_str = "";
		
	
		//	date 	= _sf.parse(_sf.format(new Date()));
			date    = new java.sql.Timestamp(new Date().getTime());
			date_str = _sf.format(date);
		
		
		if(r_btn_true.isSelected()){
			status	= 1;
		}else{
			status	= 0;
		}
		
		empsn	= txt_emp.getText().trim();
		
		String sql = " Update N_EMP_INFO t set \n" +
				     " t.advance =  " + status +
				     ", t.up_user = '"+user_up+"'"+
				     ", t.up_date = to_date('"+date_str+"','dd/MM/yyyy HH24:MI:SS')"+
					// ", t.up_date = "+date+
					 "  Where t.empsn = '" +empsn+"'";
		
		OBJ_UTILITY obj_util = new OBJ_UTILITY();
		rs	= obj_util.Exe_update_sql(sql);
		if(rs){
			OBJ_UTILITY.ShowMessageOK("Cập nhật thành công");
		}else{
			OBJ_UTILITY.ShowMessageError("Thất bại");
		}
		
		
	}

}
