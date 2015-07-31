package abhi.color;

import android.app.ExpandableListActivity;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Media;
import android.widget.CursorTreeAdapter;
import android.widget.SimpleCursorTreeAdapter;


public class ExpandableList2 extends ExpandableListActivity {

   private static final int TOKEN_GROUP = 0;
   private static final int TOKEN_CHILD = 1;

   private static final class QueryHandler extends AsyncQueryHandler {
       private CursorTreeAdapter mAdapter;

       public QueryHandler(Context context, CursorTreeAdapter adapter) {
           super(context.getContentResolver());
           this.mAdapter = adapter;
       }

       @Override
       protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
           switch (token) {
           case TOKEN_GROUP:
               mAdapter.setGroupCursor(cursor);
               break;

           case TOKEN_CHILD:
               int groupPosition = (Integer) cookie;
               mAdapter.setChildrenCursor(groupPosition, cursor);
               break;
           }
       }
   }

   public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

       // Note that the constructor does not take a Cursor. This is done to avoid querying the 
       // database on the main thread.
       public MyExpandableListAdapter(Context context, int groupLayout, int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom, int[] childrenTo) {
           super(context, null, groupLayout, groupFrom, groupTo, childLayout, childrenFrom, childrenTo);
       }

       @Override
       protected Cursor getChildrenCursor(Cursor groupCursor) {
    	   String[] columns = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME};

    		      String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";
    		      String whereVal[] = { groupCursor.getString(groupCursor.getColumnIndex(Albums.ALBUM)) };
    		      String orderBy = android.provider.MediaStore.Audio.Media.TITLE;
    		      
    		      mQueryHandler.startQuery(TOKEN_CHILD, groupCursor.getPosition(),  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
 		          where, whereVal, orderBy);

           return null;
       }
   }

   private QueryHandler mQueryHandler;
   private CursorTreeAdapter mAdapter;

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       // Set up our adapter
       mAdapter = new MyExpandableListAdapter(
               this,
               android.R.layout.simple_expandable_list_item_1,
               android.R.layout.simple_expandable_list_item_1,
               new String[] { Albums.ALBUM }, // Name for group layouts
               new int[] { android.R.id.text1 },
               new String[] { MediaStore.Audio.Media.DISPLAY_NAME }, // Number for child layouts
               new int[] { android.R.id.text1 });

       setListAdapter(mAdapter);

       mQueryHandler = new QueryHandler(this, mAdapter);
       
       
       String[] projection = new String[] { Albums._ID, Albums.ALBUM};
       String selection = null;
       String[] selectionArgs = null;
       String sortOrder = Media.ALBUM + " ASC";

       mQueryHandler.startQuery(TOKEN_GROUP, null, Albums.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
   }

   @Override
   protected void onDestroy() {
       super.onDestroy();

       // Null out the group cursor. This will cause the group cursor and all of the child cursors
       // to be closed.
       mAdapter.changeCursor(null);
       mAdapter = null;
   }
}