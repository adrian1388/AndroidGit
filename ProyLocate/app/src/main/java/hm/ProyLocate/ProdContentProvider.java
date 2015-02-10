package hm.ProyLocate;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by USUARIO-WIN on 01/02/2015.
 */
public class ProdContentProvider extends ContentProvider {

    public static final String AUTHORITY = "hm.ProyLocate.ProdContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/productos" );

    SuperDB mSuperDB = null;

    private static final int SUGGESTIONS_PROD = 1;
    private static final int SEARCH_PROD = 2;
    private static final int GET_PROD = 3;

    UriMatcher mUriMatcher = buildUriMatcher();

    private UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Suggestion items of Search Dialog is provided by this uri
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SUGGESTIONS_PROD);

        // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
        // Listview items of SearchableActivity is provided by this uri
        // See android:searchSuggestIntentData="content://in.wptrafficanalyzer.searchdialogdemo.provider/productos" of searchable.xml
        uriMatcher.addURI(AUTHORITY, "productos", SEARCH_PROD);

        // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
        // Country details for CountryActivity is provided by this uri
        // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in CountryDB.java
        uriMatcher.addURI(AUTHORITY, "productos/#", GET_PROD);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mSuperDB = new SuperDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor c = null;
        switch(mUriMatcher.match(uri)){
            case SUGGESTIONS_PROD:
                c = mSuperDB.getProductos(selectionArgs);
                break;
            case SEARCH_PROD:
                c = mSuperDB.getProductos(selectionArgs);
                break;
            case GET_PROD:
                String id = uri.getLastPathSegment();
                c = mSuperDB.getProducto(id);
        }

        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
