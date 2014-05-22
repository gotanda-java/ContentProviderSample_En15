package example.android.contentprovidersample_En15;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;



public class ProductContentProvider extends ContentProvider {

	CreateProductHelper databaseHelper;


	@Override
	public boolean onCreate(){
		databaseHelper = new CreateProductHelper(getContext());
		return true;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues values){
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.insert("product", null, values);
		return null;
	}

	@Override
	public Cursor query(Uri arg0, String[] projection, String selection,
			String[] selectionArgs, String sortOrder){
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables("product");
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
		return c;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3){
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		int row = db.update("product", arg1, arg2, arg3);
		return row;
	}


	@Override
	public int delete(Uri arg0, String arg1, String[] arg2){
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		int row = db.delete("product", arg1, arg2);
	return row;
	}


	@Override
	public String getType(Uri aggr0){
	return null;
	}

}
