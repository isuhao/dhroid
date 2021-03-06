package net.duohuo.dhroiddemos.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import net.duohuo.dhroid.activity.BaseActivity;
import net.duohuo.dhroid.db.DhDB;
import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.annotation.Inject;
import net.duohuo.dhroid.ioc.annotation.InjectExtra;
import net.duohuo.dhroid.ioc.annotation.InjectView;
import net.duohuo.dhroid.util.ViewUtil;
import net.duohuo.dhroiddemos.R;
import net.duohuo.dhroiddemos.db.bean.Student;

public class DbStudentSetActivity extends BaseActivity {
	@InjectView(id = R.id.name)
	EditText nameV;
	@InjectView(id = R.id.num)
	EditText numV;
	@InjectView(id = R.id.sex)
	EditText sexV;
	@InjectView(id = R.id.dangyuan)
	EditText dangyuanV;
	@InjectView(id = R.id.age)
	EditText ageV;
	@InjectView(id = R.id.create_time)
	TextView createTimeV;
	@InjectView(id = R.id.save, click = "onSave")
	View saveV;
	@Inject
	DhDB db;
	@InjectExtra(name = "id")
	Long id;
	Student student;
	@Inject
	IDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_set_activity);
		if (id != null && id != 0) {
			student = db.load(Student.class, id);
			ViewUtil.bindView(nameV, student.getName());
			ViewUtil.bindView(numV, student.getNum());
			ViewUtil.bindView(sexV, student.getSex());
			ViewUtil.bindView(dangyuanV, student.isDangyuang() ? "1" : "0");
			ViewUtil.bindView(ageV, student.getAge());
			ViewUtil.bindView(createTimeV, student.getCreateTime(), "toTime");

		}
	}

	/**
	 * 保存
	 * 
	 * @param v
	 */
	public void onSave(View v) {
		if (student == null) {
			student = new Student();
		}
		student.setName(nameV.getText().toString());
		student.setNum(numV.getText().toString());
		student.setSex(Integer.parseInt(sexV.getText().toString()));
		student.setAge(Integer.parseInt(ageV.getText().toString()));
		student.setDangyuang(dangyuanV.getText().toString().equals("1") ? true
				: false);
		SimpleDateFormat formdate=new SimpleDateFormat("yyyy-MM-dd");
		try {
			student.setCreateTime(formdate.parse("2013-11-08"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//保存和更新使用同一方法,原update方法保留
		db.save(student);
		dialog.showToastShort(this, "对象的主键为:"+student.getId());
		//保存和更新使用同一方法,原update方法保留
		db.save(student);
		finish();
	}
}
