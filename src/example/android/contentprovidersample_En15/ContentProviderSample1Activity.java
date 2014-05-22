package example.android.contentprovidersample_En15;

//


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;






public class ContentProviderSample1Activity extends Activity {

	CreateProductHelper helper = null;
	SQLiteDatabase db = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider_sample_en15);

		Button insertBtn = (Button) findViewById(R.id.bt_insert);
		insertBtn.setTag("insert");
		insertBtn.setOnClickListener(new BCL());

		Button updateBtn = (Button) findViewById(R.id.bt_update);
		updateBtn.setTag("update");
		updateBtn.setOnClickListener(new BCL());

		Button delBtn = (Button) findViewById(R.id.bt_delete);
		delBtn.setTag("delete");
		delBtn.setOnClickListener(new BCL());

		Button selectBtn = (Button) findViewById(R.id.bt_display);
		selectBtn.setTag("display");
		selectBtn.setOnClickListener(new BCL());

		helper = new CreateProductHelper(ContentProviderSample1Activity.this);
	}

	class BCL implements OnClickListener {
		@Override
		public void onClick(View v) {
			String tag = (String) v.getTag();

			String message = "";
			TextView label = (TextView) findViewById(R.id.tv_message);

			EditText productid = (EditText) findViewById(R.id.et_id);
			EditText name = (EditText) findViewById(R.id.et_name);
			EditText price = (EditText) findViewById(R.id.et_price);

			TableLayout tablelayout = (TableLayout) findViewById(R.id.tl_list);
			tablelayout.removeAllViews();
			db = helper.getWritableDatabase();		//db file を作る

			if (tag.equals("insert")) {
				try {
					String sql = "create table product ("
							+ "_id integer primary key autoincrement,"
							+ "productid text not null,"
							+ "name text not null,"
							+ "price integer default 0)";

					db.execSQL(sql);

					message = "テーブルを作成しました！\n";
				} catch (Exception e) {
					message = "テーブルは作成されています！\n";
					Log.e("ERROR", e.toString());
				}

				try {
					db.beginTransaction();

					ContentValues val = new ContentValues();
					val.put("productid", productid.getText().toString());
					val.put("name", name.getText().toString());
					val.put("price", price.getText().toString());

					db.insert("product", null, val);
					db.setTransactionSuccessful();
					db.endTransaction();

					message += "データを登録しました！";
				} catch (Exception e) {
					message = "データ登録に失敗しました！";
					Log.e("ERROR", e.toString());
				}



			} else if (tag.endsWith("update")) {
				try {
					String condition = null;
					if (productid != null && !productid.equals("")) {
						condition = "productid = '"
								+ productid.getText().toString() + "'";
					}

					db.beginTransaction();
					ContentValues val = new ContentValues();

					val.put("name", name.getText().toString());
					val.put("price", price.getText().toString());

					db.update("product", val, condition, null);
					db.setTransactionSuccessful();
					db.endTransaction();

					message += "データを更新しました！";
				} catch (Exception e) {
					message = "データ更新に失敗しました！";
					Log.e("ERROR", e.toString());
				}
			} else if (tag.endsWith("delete")) {

				try {
					String condition = null;
					if (productid != null && !productid.equals("")) {
						condition = "productid = '"
								+ productid.getText().toString() + "'";
					}

					db.beginTransaction();
					db.delete("product", condition, null);
					db.setTransactionSuccessful();
					db.endTransaction();

					message = "データ削除しました！";
				} catch (Exception e) {
					message = "データ削除に失敗しました！";
					Log.e("ERROR", e.toString());
				}

			} else if (tag.endsWith("display")) {

				try {
					db = helper.getReadableDatabase();
					String columns[] = { "productid", "name", "price" };
					Cursor cursor = db.query("product", columns, null, null,
							null, null, "productid DESC");

//					String sql2 = "SELECT 'productid', 'name', 'price', "
//							+"FROM product"
//							+"WHERE"
//							+"GROUP BY"
//							+"HAVING"
//							+"ORDER BY"    ;
//					db.execSQL(sql2);


					tablelayout.setStretchAllColumns(true);

					TableRow headrow = new TableRow(ContentProviderSample1Activity.this);

					TextView headtxt1 = new TextView(ContentProviderSample1Activity.this);
					headtxt1.setText("商品ID");
					headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
					headtxt1.setWidth(60);

					TextView headtxt2 = new TextView(ContentProviderSample1Activity.this);
					headtxt2.setText("商品名");
					headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
					headtxt2.setWidth(100);

					TextView headtxt3 = new TextView(ContentProviderSample1Activity.this);
					headtxt3.setText("価格");
					headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
					headtxt3.setWidth(60);

					headrow.addView(headtxt1);
					headrow.addView(headtxt2);
					headrow.addView(headtxt3);
					tablelayout.addView(headrow);

					while (cursor.moveToNext()) {

						TableRow row = new TableRow(ContentProviderSample1Activity.this);
						TextView productidtxt = new TextView(
								ContentProviderSample1Activity.this);
						productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
						productidtxt.setText(cursor.getString(0));
						TextView nametxt = new TextView(ContentProviderSample1Activity.this);
						nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
						nametxt.setText(cursor.getString(1));
						TextView pricetxt = new TextView(ContentProviderSample1Activity.this);
						pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
						pricetxt.setText(cursor.getString(2));

						row.addView(productidtxt);
						row.addView(nametxt);
						row.addView(pricetxt);
						tablelayout.addView(row);

						message = "データ取得しました！";
					}
				} catch (Exception e) {
					message = "データ取得に失敗しました！";
					Log.e("ERROR_disp", e.toString());
				}
			}
			db.close();
			label.setText(message);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_provider_sample_en15, menu);
		return true;
	}

}
