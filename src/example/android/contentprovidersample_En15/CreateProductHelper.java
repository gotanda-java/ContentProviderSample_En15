package example.android.contentprovidersample_En15;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateProductHelper extends SQLiteOpenHelper {

	public CreateProductHelper(Context con) {
		super(con, "dbsample", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
