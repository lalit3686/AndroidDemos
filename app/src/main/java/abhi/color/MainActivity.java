package abhi.color;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity implements OnItemClickListener {
	Cursor cursor;
	ListView albumlist;
	ImageView coverAlbum;
	 private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
	 private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album);

		albumlist = (ListView) findViewById(R.id.albumlist);
		coverAlbum =(ImageView)findViewById(R.id.imageviewthumbnail);

		String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
				android.provider.MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART };

		cursor = managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
				columns, null, null, null);

		setThumbNail();
		
		String[] displayFields = new String[] { MediaStore.Audio.Albums.ALBUM,  MediaStore.Audio.Albums.ALBUM_ART };
		int[] displayViews = new int[] { R.id.album_title, R.id.album_image};
		albumlist.setAdapter(new SimpleCursorAdapter(this,
				R.layout.album_row, cursor, displayFields,
				displayViews));
		albumlist.setOnItemClickListener(this);
	}

	private void setThumbNail() {
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Log.e("Album ID", cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Albums._ID)));
			cursor.moveToNext();
		}
		
		/*cursor.moveToPosition(1);
		String coverPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
		if(coverPath != null)
		Log.e("Art Path", coverPath);
	    Drawable img = Drawable.createFromPath(coverPath);
	    coverAlbum.setImageDrawable(img);*/
		
		coverAlbum.setImageBitmap(getArtworkQuick(this, 90, 100, 100));
	}
	
	private static Bitmap getArtworkQuick(Context context, long album_id, int w, int h) {
        // NOTE: There is in fact a 1 pixel border on the right side in the ImageView
        // used to display this drawable. Take it into account now, so we don't have to
        // scale later.
        w -= 1;
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            ParcelFileDescriptor fd = null;
            try {
                fd = res.openFileDescriptor(uri, "r");
                int sampleSize = 1;
                
                // Compute the closest power-of-two scale factor 
                // and pass that to sBitmapOptionsCache.inSampleSize, which will
                // result in faster decoding and better quality
                sBitmapOptionsCache.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);
                int nextWidth = sBitmapOptionsCache.outWidth >> 1;
                int nextHeight = sBitmapOptionsCache.outHeight >> 1;
                while (nextWidth>w && nextHeight>h) {
                    sampleSize <<= 1;
                    nextWidth >>= 1;
                    nextHeight >>= 1;
                }

                sBitmapOptionsCache.inSampleSize = sampleSize;
                sBitmapOptionsCache.inJustDecodeBounds = false;
                Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);

                if (b != null) {
                    // finally rescale to exactly the size we need
                    if (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h) {
                        Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
                        // Bitmap.createScaledBitmap() can return the same bitmap
                        if (tmp != b) b.recycle();
                        b = tmp;
                    }
                }
                
                return b;
            } catch (FileNotFoundException e) {
            } finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (cursor.moveToPosition(position)) {

			String[] columns = { MediaStore.Audio.Media.DATA,
					MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
					MediaStore.Audio.Media.DISPLAY_NAME,
					MediaStore.Audio.Media.MIME_TYPE, };

			String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";

			String whereVal[] = { cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Albums.ALBUM)) };

			String orderBy = android.provider.MediaStore.Audio.Media.TITLE;

			cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					columns, where, whereVal, orderBy);

			String[] displayFields = new String[] { MediaStore.Audio.Media.DISPLAY_NAME };
			int[] displayViews = new int[] { android.R.id.text1 };
			albumlist.setAdapter(new SimpleCursorAdapter(this,
					android.R.layout.simple_list_item_1, cursor, displayFields,
					displayViews));

			cursor.moveToFirst();
			Log.e("data", cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)));
		}
	}
}
